package com.handmade.ecommerce.ledger.service.impl;

import com.handmade.ecommerce.ledger.configuration.dao.LedgerDao;
import com.handmade.ecommerce.ledger.configuration.dao.LedgerLineDao;
import com.handmade.ecommerce.ledger.configuration.dao.LedgerRepository;
import com.handmade.ecommerce.ledger.model.CreateLedgerRequest;
import com.handmade.ecommerce.ledger.model.Ledger;
import com.handmade.ecommerce.ledger.model.LedgerEntry;
import com.handmade.ecommerce.ledger.model.LedgerLine;
import com.handmade.ecommerce.ledger.service.LedgerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LedgerServiceImpl implements LedgerService {
    private static final Logger logger = LoggerFactory.getLogger(LedgerServiceImpl.class);

    @Autowired
    private LedgerDao ledgerDao;

    @Autowired
    private LedgerLineDao ledgerLineDao;

    @Autowired
    private LedgerRepository ledgerRepository; // Keep for backward compatibility if needed

    @Override
    @Transactional
    public Ledger createDoubleEntry(CreateLedgerRequest request) {
        // Validation
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        Ledger ledger = new Ledger();
        ledger.setTransactionDate(LocalDateTime.now());
        ledger.setReferenceId(request.getReferenceId());
        ledger.setReferenceType(request.getReferenceType());
        ledger.setDescription(request.getDescription());
        ledger.setTotalAmount(request.getAmount());
        ledger.setCurrency(request.getCurrency());
        ledger.setStatus(request.getStatus());

        // Line 1: DEBIT (Money leaving debitAccount)
        LedgerLine debitLine = new LedgerLine();
        debitLine.setAccount(request.getDebitAccount());
        debitLine.setEntryType("DEBIT");
        debitLine.setAmount(request.getAmount());
        debitLine.setCurrency(request.getCurrency());
        debitLine.setDescription(request.getDescription());
        ledger.addLine(debitLine);

        // Line 2: CREDIT (Money entering creditAccount)
        LedgerLine creditLine = new LedgerLine();
        creditLine.setAccount(request.getCreditAccount());
        creditLine.setEntryType("CREDIT");
        creditLine.setAmount(request.getAmount());
        creditLine.setCurrency(request.getCurrency());
        creditLine.setDescription(request.getDescription());
        ledger.addLine(creditLine);

        // Save (Cascade will save lines)
        ledger = ledgerDao.save(ledger);

        logger.info("Created ledger transaction: {} for reference: {}", ledger.getId(), request.getReferenceId());

        return ledger;
    }

    @Override
    @Transactional
    public void recordPayIn(String sellerId, BigDecimal amount, String currency, String referenceId) {
        // Entry 1: Platform Account (CREDIT) - Money arrived
        CreateLedgerRequest platformCredit = new CreateLedgerRequest(
                "PLATFORM_PAYABLE", // Debit
                "PLATFORM_ACCOUNT", // Credit (money in)
                amount,
                currency,
                "PENDING",
                "Payment received for order: " + referenceId,
                referenceId);
        platformCredit.setReferenceType("PAYMENT_ORDER");
        createDoubleEntry(platformCredit);

        // Entry 2: Seller Virtual Wallet (CREDIT) - Seller is owed
        CreateLedgerRequest sellerCredit = new CreateLedgerRequest(
                "PLATFORM_PAYABLE", // Debit (liability)
                "SELLER_" + sellerId + "_WALLET", // Credit (virtual balance)
                amount,
                currency,
                "PENDING",
                "Virtual wallet credit for order: " + referenceId,
                referenceId);
        sellerCredit.setReferenceType("PAYMENT_ORDER");
        createDoubleEntry(sellerCredit);

        logger.info("Recorded pay-in for seller: {}, amount: {}, reference: {}", sellerId, amount, referenceId);
    }

    @Override
    @Deprecated
    public void createDoubleEntry(String paymentOrderId, String buyerId, String sellerId, BigDecimal amount,
            String currency) {
        // Adapt old call to new structure
        CreateLedgerRequest request = new CreateLedgerRequest(
                buyerId, // Debit buyer
                sellerId, // Credit seller
                amount,
                currency,
                "PENDING",
                "Legacy payment distribution",
                paymentOrderId);
        createDoubleEntry(request);
    }

    @Override
    public List<LedgerEntry> getEntriesByPaymentOrder(String paymentOrderId) {
        // This still uses the old repository/entity.
        // Ideally we should migrate this to return List<Ledger> or map Ledger to
        // LedgerEntry
        return ledgerRepository.findByPaymentOrderId(paymentOrderId);
    }

    @Override
    public boolean verifyDoubleEntry(String transactionId) {
        // Verify using new structure if transactionId refers to Ledger ID
        // Or old structure. For now, let's assume we want to verify the new Ledger
        // structure
        // But the interface expects transactionId string.

        // Let's check Ledger table first
        return ledgerDao.findById(transactionId)
                .map(ledger -> {
                    BigDecimal debits = ledger.getLines().stream()
                            .filter(l -> "DEBIT".equals(l.getEntryType()))
                            .map(LedgerLine::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal credits = ledger.getLines().stream()
                            .filter(l -> "CREDIT".equals(l.getEntryType()))
                            .map(LedgerLine::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return debits.compareTo(credits) == 0;
                })
                .orElseGet(() -> {
                    // Fallback to old repository
                    BigDecimal sum = ledgerRepository.verifyDoubleEntry(transactionId);
                    return sum != null && sum.compareTo(BigDecimal.ZERO) == 0;
                });
    }

    @Override
    public BigDecimal getAccountBalance(String accountId) {
        // Use new DAO
        BigDecimal balance = ledgerLineDao.getAccountBalance(accountId);
        if (balance == null) {
            // Fallback to old DAO
            balance = ledgerRepository.getAccountBalance(accountId);
        }
        return balance != null ? balance : BigDecimal.ZERO;
    }
}