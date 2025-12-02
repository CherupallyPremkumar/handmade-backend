package com.handmade.ecommerce.payment.executor;

import com.handmade.ecommerce.payment.model.CheckoutSession;
import com.handmade.ecommerce.payment.model.PaymentOrder;
import com.handmade.ecommerce.razorpay.service.RazorpayPaymentService;
import com.handmade.ecommerce.stripe.service.StripePaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Payment Executor
 * Creates checkout sessions with appropriate PSP based on currency
 * 
 * Following Alex Xu's design - Strategy pattern for PSP selection
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentExecutor {

    private final RazorpayPaymentService razorpayPaymentService;
    private final StripePaymentService stripePaymentService;

    /**
     * Create checkout session with appropriate PSP
     * Returns URL where user should be redirected to complete payment
     * 
     * Strategy:
     * - INR → Razorpay
     * - USD/EUR/GBP → Stripe
     * 
     * @param order Payment order
     * @return CheckoutSession with URL for user redirection
     */
    public CheckoutSession createCheckoutSession(PaymentOrder order) {
        log.info("Creating checkout session for order: {}, currency: {}",
                order.getId(), order.getCurrency());

        try {
            CheckoutSession session;

            // Strategy pattern: Select PSP based on currency
            if (razorpayPaymentService.supportsCurrency(order.getCurrency())) {
                log.info("Using Razorpay for currency: {}", order.getCurrency());
                session = razorpayPaymentService.createCheckoutSession(
                        order.getId(),
                        order.getAmount(),
                        order.getCurrency());
            } else if (stripePaymentService.supportsCurrency(order.getCurrency())) {
                log.info("Using Stripe for currency: {}", order.getCurrency());
                session = stripePaymentService.createCheckoutSession(
                        order.getId(),
                        order.getAmount(),
                        order.getCurrency());
            } else {
                throw new RuntimeException("Unsupported currency: " + order.getCurrency());
            }

            log.info("Checkout session created - SessionId: {}, PSP: {}",
                    session.getSessionId(), session.getPspName());

            return session;

        } catch (Exception e) {
            log.error("Failed to create checkout session for order: {}", order.getId(), e);
            throw new RuntimeException("Checkout session creation failed: " + e.getMessage(), e);
        }
    }
}