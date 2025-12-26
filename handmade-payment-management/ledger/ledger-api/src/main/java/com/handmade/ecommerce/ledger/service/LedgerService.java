package com.handmade.ecommerce.ledger.service;

import com.handmade.ecommerce.ledger.model.CreateLedgerRequest;
import com.handmade.ecommerce.ledger.model.Ledger;
import com.handmade.ecommerce.ledger.model.LedgerEntry;

import java.math.BigDecimal;
import java.util.List;

public interface LedgerService {

    /**
     * Create double-entry ledger transaction using request object
     */
    Ledger createDoubleEntry(CreateLedgerRequest request);

    /**
     * Record pay-in transaction (customer payment received)
     * Creates 3 ledger entries automatically:
     * 1. Platform Account (CREDIT) - money arrived
     * 2. Seller Virtual Wallet (CREDIT) - seller is owed
     * 3. Platform Payable (DEBIT) - liability created
     * 
     * @param sellerId    Seller ID
     * @param amount      Amount received
     * @param currency    Currency code
     * @param referenceId Reference ID (PaymentOrder ID)
     */
    void recordPayIn(String sellerId, BigDecimal amount, String currency, String referenceId);

    /**
     * @deprecated Use createDoubleEntry(CreateLedgerRequest) instead
     */
    @Deprecated
    void createDoubleEntry(
            String paymentOrderId,
            String buyerId,
            String sellerId,
            BigDecimal amount,
            String currency);

    List<LedgerEntry> getEntriesByPaymentOrder(String paymentOrderId);

    boolean verifyDoubleEntry(String transactionId);

    BigDecimal getAccountBalance(String accountId);

}
