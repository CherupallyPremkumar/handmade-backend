// ledger-service/configuration/dao/LedgerRepository.java
package com.handmade.ecommerce.ledger.configuration.dao;

import com.handmade.ecommerce.ledger.model.LedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface LedgerRepository extends JpaRepository<LedgerEntry, String> {

    List<LedgerEntry> findByPaymentOrderId(String paymentOrderId);

    List<LedgerEntry> findByAccountId(String accountId);

    List<LedgerEntry> findByTransactionId(String transactionId);

    /**
     * Verify double-entry: sum should be 0
     */
    @Query("SELECT SUM(CASE WHEN l.entryType = 'DEBIT' THEN -l.amount ELSE l.amount END) " +
            "FROM LedgerEntry l WHERE l.transactionId = :transactionId")
    BigDecimal verifyDoubleEntry(String transactionId);

    /**
     * Calculate account balance: sum of credits minus debits
     */
    @Query("SELECT COALESCE(SUM(CASE WHEN l.entryType = 'CREDIT' THEN l.amount ELSE -l.amount END), 0) " +
            "FROM LedgerEntry l WHERE l.accountId = :accountId")
    BigDecimal getAccountBalance(String accountId);
}