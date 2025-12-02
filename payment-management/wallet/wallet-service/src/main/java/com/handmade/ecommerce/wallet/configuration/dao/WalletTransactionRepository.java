package com.handmade.ecommerce.wallet.configuration.dao;

import com.handmade.ecommerce.wallet.model.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for WalletTransaction entity
 * Handles database operations for wallet transaction history
 */
@Repository
public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, String> {
    
    /**
     * Find all transactions for a wallet, ordered by creation date (newest first)
     */
    List<WalletTransaction> findByWalletIdOrderByCreatedAtDesc(String walletId);
    
    /**
     * Find transaction by payment order ID
     */
    List<WalletTransaction> findByPaymentOrderId(String paymentOrderId);
    
    /**
     * Find transactions by wallet and transaction type
     */
    List<WalletTransaction> findByWalletIdAndTransactionType(String walletId, String transactionType);
    
    /**
     * Find all transactions by transaction type
     */
    List<WalletTransaction> findByTransactionType(String transactionType);
}
