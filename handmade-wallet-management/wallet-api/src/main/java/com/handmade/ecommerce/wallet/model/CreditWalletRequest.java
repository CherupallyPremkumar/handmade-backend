package com.handmade.ecommerce.wallet.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Request object for crediting a seller's wallet.
 * 
 * Used when payment is captured and seller's wallet needs to be credited
 * with their share of the payment.
 */
public class CreditWalletRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Seller's user ID
     */
    private String userId;

    /**
     * PaymentOrder ID (for tracking)
     */
    private String paymentOrderId;

    /**
     * Amount to credit
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
     * Transaction status (PENDING, AVAILABLE, etc.)
     */
    private String status;

    // Constructors
    public CreditWalletRequest() {
    }

    public CreditWalletRequest(String userId, String paymentOrderId, BigDecimal amount,
            String currency, String description, String status) {
        this.userId = userId;
        this.paymentOrderId = paymentOrderId;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
        this.status = status;
    }

    /**
     * Simplified constructor for pay-in
     * Automatically sets status to PENDING and creates description
     */
    public CreditWalletRequest(String userId, String paymentOrderId, BigDecimal amount, String currency) {
        this.userId = userId;
        this.paymentOrderId = paymentOrderId;
        this.amount = amount;
        this.currency = currency;
        this.description = "Payment captured for order: " + paymentOrderId;
        this.status = "PENDING";
    }

    // Getters and Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentOrderId() {
        return paymentOrderId;
    }

    public void setPaymentOrderId(String paymentOrderId) {
        this.paymentOrderId = paymentOrderId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
