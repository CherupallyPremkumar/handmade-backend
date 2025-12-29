package com.handmade.ecommerce.orchestrator.service.cmd;

import com.handmade.ecommerce.command.CheckoutSessionResponse;
import com.handmade.ecommerce.command.PaymentResponse;
import com.handmade.ecommerce.orchestrator.PaymentExchange;
import com.handmade.ecommerce.payment.model.Payment;
import com.handmade.ecommerce.payment.model.PaymentSession;
import com.handmade.ecommerce.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.chenile.owiz.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Checks if payment request has already been processed (idempotency).
 *
 * Prevents double payment when:
 * - User clicks "Pay" button multiple times
 * - Network timeout causes retry
 * - Browser refresh on payment page
 *
 * How it works:
 * 1. Extract idempotency key from request
 * 2. Query Payment module for existing payment with this key
 * 3. If found:
 * a. Check if session is still active (not expired)
 * b. If active: Return cached session
 * c. If expired: Create new session
 * 4. If not found:
 * - First time seeing this key
 * - Continue to next command
 */
@Component
@Slf4j
public class CheckIdempotency implements Command<PaymentExchange> {

    @Autowired
    private PaymentService paymentService;

    @Override
    public void execute(PaymentExchange context) throws Exception {
        try {
            String idempotencyKey = context.getRequest().getIdempotencyKey();

            // Check if payment already exists
            Payment existingPayment = paymentService.findByIdempotencyKey(idempotencyKey);

            if (existingPayment != null) {
                log.info("Duplicate payment request detected. IdempotencyKey: {}, PaymentId: {}",
                        idempotencyKey, existingPayment.getId());

                PaymentSession session = existingPayment.getPaymentSession();

                // Check if session exists and is still active
                if (session != null && session.isActive()) {
                    log.info("Existing session is active. Returning cached session. SessionId: {}, ExpiresAt: {}",
                            session.getPspSessionId(), session.getExpiresAt());

                    // Build PaymentResponse
                    PaymentResponse paymentResponse = new PaymentResponse();
                    paymentResponse.setPaymentId(existingPayment.getId());
                    paymentResponse.setStatus(existingPayment.getCurrentState().getStateId());
                    context.setResponse(paymentResponse);

                    // Build CheckoutSessionResponse from existing session
                    CheckoutSessionResponse sessionResponse = new CheckoutSessionResponse();
                    sessionResponse.setPaymentOrderId(existingPayment.getId());
                    sessionResponse.setCheckoutUrl(session.getCheckoutUrl());
                    sessionResponse.setSessionId(session.getPspSessionId());
                    context.setCheckoutSessionResponse(sessionResponse);

                    // Mark as idempotent to skip remaining commands
                    context.setIdempotentRequest(true);

                    log.info("Returning existing checkout session. PaymentId: {}, SessionId: {}, CheckoutUrl: {}",
                            existingPayment.getId(),
                            session.getPspSessionId(),
                            session.getCheckoutUrl());

                    return;

                } else {
                    // Session expired - create new session
                    log.warn("Existing session expired or not found. PaymentId: {}, SessionExpiry: {}",
                            existingPayment.getId(),
                            session != null ? session.getExpiresAt() : "N/A");

                    log.info("Creating new session for existing payment. PaymentId: {}",
                            existingPayment.getId());

                    // Set payment response but allow new session creation
                    PaymentResponse paymentResponse = new PaymentResponse();
                    paymentResponse.setPaymentId(existingPayment.getId());
                    paymentResponse.setStatus(existingPayment.getCurrentState().getStateId());
                    context.setResponse(paymentResponse);

                    // Don't mark as idempotent - allow PSP registration to create new session
                    context.setIdempotentRequest(false);

                    return;
                }
            }

            // New request - continue to next command
            context.setIdempotentRequest(false);

        } catch (Exception e) {
            context.setException(e);
            throw e;
        }
    }
}