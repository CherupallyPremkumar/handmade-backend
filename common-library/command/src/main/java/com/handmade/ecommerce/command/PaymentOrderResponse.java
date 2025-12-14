package com.handmade.ecommerce.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO for individual payment order.
 * Represents status of payment to one seller.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrderResponse {

    /**
     * Payment Order ID (same as request - idempotency key)
     */
    private String paymentOrderId;

    /**
     * Seller ID
     */
    private String sellerId;

    /**
     * Payment order status
     * NOT_STARTED, EXECUTING, SUCCESS, FAILED
     */
    private String status;

    /**
     * PSP token/reference ID
     * Returned by PSP after successful payment
     */
    private String pspToken;

    /**
     * Amount
     */
    private String amount;

    /**
     * Currency
     */
    private String currency;

    /**
     * Error message (if failed)
     */
    private String errorMessage;

    /**
     * Retry count (for failed payments)
     */
    private Integer retryCount;

    /**
     * Checkout URL (for hosted payment page)
     */
    private String checkoutUrl;
}
