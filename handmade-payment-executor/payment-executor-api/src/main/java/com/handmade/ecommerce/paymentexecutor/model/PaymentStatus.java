package com.handmade.ecommerce.paymentexecutor.model;

/**
 * PaymentStatus
 * 
 * Represents the status of a payment or refund from PSP perspective.
 * This is different from PaymentOrderStatus which is internal to our system.
 */
public enum PaymentStatus {
    
    /**
     * Payment is pending user action (user hasn't completed payment yet)
     */
    PENDING,
    
    /**
     * Payment is being processed by PSP
     */
    PROCESSING,
    
    /**
     * Payment was authorized but not yet captured
     */
    AUTHORIZED,
    
    /**
     * Payment was successfully captured/completed
     */
    CAPTURED,
    
    /**
     * Payment failed
     */
    FAILED,
    
    /**
     * Payment was cancelled
     */
    CANCELLED,
    
    /**
     * Payment expired (user didn't complete within time limit)
     */
    EXPIRED,
    
    /**
     * Refund initiated
     */
    REFUND_INITIATED,
    
    /**
     * Refund completed
     */
    REFUNDED,
    
    /**
     * Refund failed
     */
    REFUND_FAILED,
    
    /**
     * Status unknown or not recognized
     */
    UNKNOWN
}
