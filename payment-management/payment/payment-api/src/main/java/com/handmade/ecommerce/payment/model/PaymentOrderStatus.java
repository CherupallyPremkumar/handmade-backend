package com.handmade.ecommerce.payment.model;

/**
 * Payment Order Status
 * Tracks the lifecycle of a payment order
 * 
 * Flow:
 * NOT_STARTED → PENDING_USER_ACTION → EXECUTING → SUCCESS/FAILED
 */
public enum PaymentOrderStatus {

    /**
     * Initial state - Payment order created but not yet registered with PSP
     */
    NOT_STARTED,

    /**
     * Waiting for user to complete payment on PSP hosted page
     * User has been redirected to PSP, but hasn't paid yet
     */
    PENDING_USER_ACTION,

    /**
     * PSP is processing the payment
     * User has submitted payment, PSP is validating/charging
     */
    EXECUTING,

    /**
     * Payment completed successfully
     */
    SUCCESS,

    /**
     * Payment failed
     * - PSP rejected
     * - Network error
     * - Validation error
     */
    FAILED
}
