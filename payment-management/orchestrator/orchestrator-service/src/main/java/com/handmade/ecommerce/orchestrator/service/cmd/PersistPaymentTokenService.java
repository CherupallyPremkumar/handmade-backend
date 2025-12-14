package com.handmade.ecommerce.orchestrator.service.cmd;

import com.handmade.ecommerce.command.CheckoutSessionResponse;
import com.handmade.ecommerce.orchestrator.PaymentExchange;
import com.handmade.ecommerce.payment.model.PaymentSession;
import com.handmade.ecommerce.payment.service.PaymentService;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Persists PSP checkout session details to PaymentSession entity.
 * 
 * This command:
 * 1. Retrieves checkout session from context (created by
 * PspRegistrationService)
 * 2. Creates PaymentSession entity with:
 * - PSP type (RAZORPAY, STRIPE, etc.)
 * - Session ID (PSP's session identifier)
 * - Checkout URL (where user will pay)
 * 3. Associates PaymentSession with Payment entity
 * 
 * These details are needed for:
 * - Redirecting user to payment page
 * - Tracking payment in PSP
 * - Reconciliation
 * - Idempotency (returning existing session)
 */
@Component
public class PersistPaymentTokenService implements Command<PaymentExchange> {

    private static final Logger log = LoggerFactory.getLogger(PersistPaymentTokenService.class);

    @Autowired
    private PaymentService paymentService;

    @Override
    public void execute(PaymentExchange context) throws Exception {
        try {
            String paymentId = context.getResponse().getPaymentId();
            CheckoutSessionResponse sessionResponse = context.getCheckoutSessionResponse();

            if (sessionResponse == null) {
                throw new IllegalStateException("Checkout session not found in context");
            }

            log.info("Creating PaymentSession for Payment: {}, PSP: {}, SessionId: {}",
                    paymentId, sessionResponse.getPspType(), sessionResponse.getSessionId());

            // Build PaymentSession entity
            PaymentSession session = new PaymentSession();
            session.setPaymentId(paymentId);
            session.setPspType(sessionResponse.getPspType()); // ← Use PSP type from context
            session.setPspSessionId(sessionResponse.getSessionId());
            session.setCheckoutUrl(sessionResponse.getCheckoutUrl());

            paymentService.addCheckoutSession(session);

            log.info("Successfully created PaymentSession for Payment: {}", paymentId);

        } catch (Exception e) {
            log.error("Failed to create PaymentSession for Payment: {}",
                    context.getResponse().getPaymentId(), e);
            context.setException(e);
            throw e;
        }
    }
}
