package com.handmade.ecommerce.payment.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Checkout Session
 * Returned by PSP when registering a payment
 * Contains the URL where user should be redirected to complete payment
 */
@Data
@AllArgsConstructor
public class CheckoutSession {

    /**
     * PSP session/order ID
     */
    private String sessionId;

    /**
     * URL where user should be redirected to complete payment
     * User will select payment method and enter details on this page
     */
    private String checkoutUrl;

    /**
     * PSP name (RAZORPAY, STRIPE, etc.)
     */
    private String pspName;
}
