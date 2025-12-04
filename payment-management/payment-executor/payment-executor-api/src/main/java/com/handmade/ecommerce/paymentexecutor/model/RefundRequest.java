package com.handmade.ecommerce.paymentexecutor.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * RefundRequest
 * 
 * Request object for initiating a refund with PSP.
 */
public class RefundRequest implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Unique refund identifier (idempotency key)
     */
    private String refundId;
    
    /**
     * PSP's payment identifier to refund
     */
    private String pspPaymentId;
    
    /**
     * Amount to refund (can be partial)
     */
    private BigDecimal amount;
    
    /**
     * Currency code
     */
    private String currency;
    
    /**
     * Reason for refund
     */
    private String reason;
    
    /**
     * Additional notes
     */
    private String notes;
    
    // Constructors
    
    public RefundRequest() {
    }
    
    public RefundRequest(String refundId, String pspPaymentId, BigDecimal amount, String currency) {
        this.refundId = refundId;
        this.pspPaymentId = pspPaymentId;
        this.amount = amount;
        this.currency = currency;
    }
    
    // Getters and Setters
    
    public String getRefundId() {
        return refundId;
    }
    
    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }
    
    public String getPspPaymentId() {
        return pspPaymentId;
    }
    
    public void setPspPaymentId(String pspPaymentId) {
        this.pspPaymentId = pspPaymentId;
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
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public String toString() {
        return "RefundRequest{" +
                "refundId='" + refundId + '\'' +
                ", pspPaymentId='" + pspPaymentId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
