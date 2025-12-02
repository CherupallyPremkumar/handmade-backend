package com.handmade.ecommerce.command;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Payment Response
 * Returned after payment processing
 */
@Data
@Builder
public class PaymentResponse {

    /**
     * Payment event ID (parent payment ID)
     */
    private String paymentEventId;

    /**
     * Overall payment status
     * - PENDING_USER_ACTION: User needs to complete payment on PSP page
     * - SUCCESS: All orders succeeded
     * - PARTIAL: Some orders succeeded
     * - FAILED: All orders failed
     */
    private String status;

    /**
     * Checkout session ID from PSP (for hosted payment page)
     * Only present when status is PENDING_USER_ACTION
     */
    private String checkoutSessionId;

    /**
     * URL where user should be redirected to complete payment
     * Only present when status is PENDING_USER_ACTION
     */
    private String checkoutUrl;

    /**
     * Individual payment order responses
     */
    private List<PaymentOrderResponse> orders;

    /**
     * Total amount
     */
    private String totalAmount;

    /**
     * Currency code
     */
    private String currency;
}
