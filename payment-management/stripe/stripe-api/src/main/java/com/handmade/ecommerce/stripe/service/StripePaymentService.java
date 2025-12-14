package com.handmade.ecommerce.stripe.service;

import com.handmade.ecommerce.payment.model.CheckoutSession;

import java.math.BigDecimal;

/**
 * Stripe Payment Service
 * Handles payment processing via Stripe PSP (Hosted Payment Page)
 */
public interface StripePaymentService {

    /**
     * Create checkout session with Stripe
     * Returns URL where user should be redirected to complete payment
     * 
     * @param paymentOrderId Payment order ID (idempotency key)
     * @param amount         Amount to charge
     * @param currency       Currency code (USD, EUR, GBP, etc.)
     * @return CheckoutSession with URL for user redirection
     * @throws RuntimeException if session creation fails
     */
    CheckoutSession createCheckoutSession(String paymentOrderId, BigDecimal amount, String currency);

    /**
     * Check if currency is supported
     * 
     * @param currency Currency code
     * @return true if supported
     */
    boolean supportsCurrency(String currency);
}
