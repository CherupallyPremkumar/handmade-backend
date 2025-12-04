package com.handmade.ecommerce.orchestrator.service.cmd;

import com.handmade.ecommerce.orchestrator.PaymentExchange;
import com.handmade.ecommerce.orchestrator.WebhookExchange;
import com.handmade.ecommerce.payment.model.Payment;
import com.handmade.ecommerce.payment.service.PaymentService;
import org.chenile.owiz.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Updates Payment status based on webhook event.
 * 
 * Webhook events:
 * - "payment.captured" → Payment successful
 * - "payment.failed" → Payment failed
 * - "payment.pending" → Payment pending (requires user action)
 * 
 * Status transitions:
 * PENDING_USER_ACTION → PROCESSING (payment captured)
 * PENDING_USER_ACTION → FAILED (payment failed)
 * 
 * Also stores:
 * - pspPaymentId: PSP's internal payment ID
 * - capturedAt: Timestamp when payment was captured
 * - failureReason: If payment failed
 */
@Component
public class UpdatePaymentStatus implements Command<WebhookExchange> {

    @Autowired
    private PaymentService paymentService;

    @Override
    public void execute(WebhookExchange context) throws Exception {
        try {
            paymentService.updatePayment(context.getParsedPayload());
        } catch (Exception e) {
            context.setException(e);
            throw e;
        }
    }
}
