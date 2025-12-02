package com.handmade.ecommerce.wallet.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "hm_wallet_transaction", indexes = {
        @Index(name = "idx_wallet_id", columnList = "wallet_id"),
        @Index(name = "idx_payment_order_id", columnList = "payment_order_id"),
        @Index(name = "idx_created_at", columnList = "created_at")
})
public class WalletTransaction extends BaseJpaEntity {

    /**
     * Reference to wallet
     */
    @Column(name = "wallet_id", nullable = false, length = 100)
    private String walletId;

    /**
     * Reference to payment order (optional)
     */
    @Column(name = "payment_order_id", length = 100)
    private String paymentOrderId;

    /**
     * Transaction type: CREDIT, DEBIT, REFUND, WITHDRAWAL
     */
    @Column(name = "transaction_type", nullable = false, length = 20)
    private String transactionType;

    /**
     * Transaction amount
     */
    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;

    /**
     * Balance before this transaction
     */
    @Column(name = "balance_before", nullable = false, precision = 19, scale = 4)
    private BigDecimal balanceBefore;

    /**
     * Balance after this transaction
     */
    @Column(name = "balance_after", nullable = false, precision = 19, scale = 4)
    private BigDecimal balanceAfter;

    /**
     * Transaction description
     */
    @Column(name = "description", length = 500)
    private String description;

    /**
     * Additional metadata (JSON)
     */
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;
}