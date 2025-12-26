package com.handmade.ecommerce.orchestrator.service.cmd;

import com.handmade.ecommerce.command.PaymentResponse;
import com.handmade.ecommerce.orchestrator.PaymentExchange;
import com.handmade.ecommerce.payment.service.PaymentService;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Creates Payment entity in the database.
 * 
 * This command:
 * 1. Calls Payment module to create Payment entity
 * 2. Payment module handles idempotency internally
 * 3. Stores Payment response in context
 * 
 * Payment entity stores:
 * - paymentId (unique identifier)
 * - checkoutId (reference to checkout)
 * - buyerId (who is paying)
 * - totalAmount (sum of all PaymentOrders)
 * - idempotencyKey (for duplicate prevention)
 * - status (PROCESSING)
 * 
 * Note: Payment module checks idempotency internally.
 * If idempotency key already exists, returns existing payment.
 */
@Component
public class CreatePaymentService implements Command<PaymentExchange> {

    private static final Logger log = LoggerFactory.getLogger(CreatePaymentService.class);

    @Autowired
    private PaymentService paymentService;

    @Override
    public void execute(PaymentExchange context) throws Exception {
        try {
            log.info("Creating payment for buyer: {}, amount: {}",
                    context.getRequest().getBuyerId(),
                    context.getRequest().getTotalAmount());

            // Call Payment module
            // Payment module handles idempotency check internally
            PaymentResponse response = paymentService.processPayment(context.getRequest());

            // Store response in context
            context.setResponse(response);

            log.info("Payment created successfully. PaymentId: {}", response.getPaymentId());

        } catch (Exception e) {
            log.error("Failed to create payment for buyer: {}",
                    context.getRequest().getBuyerId(), e);
            context.setException(e);
            throw e;
        }
    }
}
