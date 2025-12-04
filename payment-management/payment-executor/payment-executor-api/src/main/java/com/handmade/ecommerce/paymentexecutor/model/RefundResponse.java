package com.handmade.ecommerce.paymentexecutor.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * RefundResponse
 * 
 * Response object from PSP after initiating a refund.
 */
public class RefundResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * PSP's refund identifier
     */
    private String pspRefundId;
    
    /**
     * Refund status
     */
    private PaymentStatus status;
    
    /**
     * Refunded amount
     */
    private BigDecimal amount;
    
    /**
     * Currency code
     */
    private String currency;
    
    /**
     * PSP's payment identifier that was refunded
     */
    private String pspPaymentId;
    
    /**
     * Error message if refund failed
     */
    private String errorMessage;
    
    /**
     * PSP type
     */
    private String pspType;
    
    // Constructors
    
    public RefundResponse() {
    }
    
    public RefundResponse(String pspRefundId, PaymentStatus status) {
        this.pspRefundId = pspRefundId;
        this.status = status;
    }
    
    // Getters and Setters
    
    public String getPspRefundId() {
        return pspRefundId;
    }
    
    public void setPspRefundId(String pspRefundId) {
        this.pspRefundId = pspRefundId;
    }
    
    public PaymentStatus getStatus() {
        return status;
    }
    
    public void setStatus(PaymentStatus status) {
        this.status = status;
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
    
    public String getPspPaymentId() {
        return pspPaymentId;
    }
    
    public void setPspPaymentId(String pspPaymentId) {
        this.pspPaymentId = pspPaymentId;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public String getPspType() {
        return pspType;
    }
    
    public void setPspType(String pspType) {
        this.pspType = pspType;
    }
    
    @Override
    public String toString() {
        return "RefundResponse{" +
                "pspRefundId='" + pspRefundId + '\'' +
                ", status=" + status +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", pspPaymentId='" + pspPaymentId + '\'' +
                ", pspType='" + pspType + '\'' +
                '}';
    }
}
