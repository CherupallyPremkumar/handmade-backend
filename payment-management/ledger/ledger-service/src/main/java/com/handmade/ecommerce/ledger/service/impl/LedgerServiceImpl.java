package com.handmade.ecommerce.ledger.service.impl;

import com.handmade.ecommerce.ledger.model.LedgerEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.ledger.model.Ledger;
import com.handmade.ecommerce.ledger.service.LedgerService;

import com.handmade.ecommerce.ledger.configuration.dao.LedgerRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class LedgerServiceImpl implements LedgerService{
    private static final Logger logger = LoggerFactory.getLogger(LedgerServiceImpl.class);

    @Autowired
    LedgerRepository ledgerRepository;
    @Override
    public void createDoubleEntry(String paymentOrderId, String buyerId, String sellerId, BigDecimal amount, String currency) {

        // Generate unique transaction ID to link the two entries
        String transactionId = UUID.randomUUID().toString();

        // Entry 1: DEBIT from buyer (money out)
        LedgerEntry debitEntry = new LedgerEntry();
        debitEntry.setTransactionId(transactionId);
        debitEntry.setPaymentOrderId(paymentOrderId);
        debitEntry.setAccountId(buyerId);
        debitEntry.setAccountType("BUYER");
        debitEntry.setEntryType("DEBIT");
        debitEntry.setAmount(amount);
        debitEntry.setCurrency(currency);
        debitEntry.setDescription("Payment to seller " + sellerId);

        // Entry 2: CREDIT to seller (money in)
        LedgerEntry creditEntry = new LedgerEntry();
        creditEntry.setTransactionId(transactionId);
        creditEntry.setPaymentOrderId(paymentOrderId);
        creditEntry.setAccountId(sellerId);
        creditEntry.setAccountType("SELLER");
        creditEntry.setEntryType("CREDIT");
        creditEntry.setAmount(amount);
        creditEntry.setCurrency(currency);
        creditEntry.setDescription("Payment from buyer " + buyerId);

        // Save both entries
        ledgerRepository.save(debitEntry);
        ledgerRepository.save(creditEntry);

        // Verify double-entry integrity
        boolean isValid = verifyDoubleEntry(transactionId);
        if (!isValid) {
            logger.error("Double-entry verification failed for transaction: {}", transactionId);
            throw new IllegalStateException("Double-entry verification failed");
        }

        logger.info("Double-entry created successfully. Transaction ID: {}", transactionId);
    }

    @Override
    public List<LedgerEntry> getEntriesByPaymentOrder(String paymentOrderId) {
        return ledgerRepository.findByPaymentOrderId(paymentOrderId);
    }

    @Override
    public boolean verifyDoubleEntry(String transactionId) {
        BigDecimal sum = ledgerRepository.verifyDoubleEntry(transactionId);
        // Sum should be exactly 0 for valid double-entry
        return sum != null && sum.compareTo(BigDecimal.ZERO) == 0;
    }

    @Override
    public BigDecimal getAccountBalance(String accountId) {
        BigDecimal balance = ledgerRepository.getAccountBalance(accountId);
        return balance != null ? balance : BigDecimal.ZERO;
    }
}