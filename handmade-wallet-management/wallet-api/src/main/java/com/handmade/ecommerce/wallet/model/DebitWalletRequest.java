package com.handmade.ecommerce.wallet.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Request object for debiting a seller's wallet.
 * 
 * Used when seller requests payout or when refunds need to be processed.
 */
public class DebitWalletRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Seller's user ID
     */
    private String userId;

    /**
     * Reference ID (payout ID, refund ID, etc.)
     */
    private String referenceId;

    /**
     * Amount to debit
     */
    private BigDecimal amount;

    /**
     * Currency code (INR, USD, etc.)
     */
    private String currency;

    /**
     * Transaction description
     */
    private String description;

    /**
     * Transaction type (PAYOUT, REFUND, ADJUSTMENT, etc.)
     */
    private String type;

    // Constructors
    public DebitWalletRequest() {
    }

    public DebitWalletRequest(String userId, String referenceId, BigDecimal amount,
            String currency, String description, String type) {
        this.userId = userId;
        this.referenceId = referenceId;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.type = type;
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
