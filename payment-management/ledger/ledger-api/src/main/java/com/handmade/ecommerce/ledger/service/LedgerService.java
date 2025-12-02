package com.handmade.ecommerce.ledger.service;

import com.handmade.ecommerce.ledger.model.Ledger;
import com.handmade.ecommerce.ledger.model.LedgerEntry;

import java.math.BigDecimal;
import java.util.List;

public interface LedgerService {

    void createDoubleEntry(
            String paymentOrderId,
            String buyerId,
            String sellerId,
            BigDecimal amount,
            String currency
    );

    List<LedgerEntry> getEntriesByPaymentOrder(String paymentOrderId);

    boolean verifyDoubleEntry(String transactionId);

    BigDecimal getAccountBalance(String accountId);

}
