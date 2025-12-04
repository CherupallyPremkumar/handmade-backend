package com.handmade.ecommerce.orchestrator.service.cmd;

import com.handmade.ecommerce.command.WebhookRequest;
import com.handmade.ecommerce.orchestrator.WebhookExchange;
import com.handmade.ecommerce.paymentexecutor.PaymentExecutor;
import com.handmade.ecommerce.paymentexecutor.PaymentExecutorFactory;
import com.handmade.ecommerce.command.WebhookPayload;
import org.chenile.owiz.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Parses webhook payload from PSP to extract payment information.
 * Delegates to PSP-specific PaymentExecutor for parsing.
 */
@Component
public class ParseWebhookPayload implements Command<WebhookExchange> {

    @Autowired
    private PaymentExecutorFactory paymentExecutorFactory;

    @Override
    public void execute(WebhookExchange context) throws Exception {
        try {
            // Get the already-verified WebhookRequest from context
            WebhookRequest request = context.getConvertedRequest();
            if (request == null) {
                throw new IllegalStateException(
                        "WebhookRequest not found in context. VerifyWebhookSignature must run first.");
            }

            String pspType = request.getPspType();
            String payloadString = request.getPayload();

            // Get PSP-specific executor
            PaymentExecutor executor = paymentExecutorFactory.getExecutor(pspType);
            if (executor == null) {
                throw new IllegalArgumentException("No executor found for PSP type: " + pspType);
            }

            // Parse payload using PSP-specific executor
            WebhookPayload parsedPayload = executor.parseWebhookPayload(payloadString);

            // Store parsed data in context
            context.setParsedPayload(parsedPayload);

        } catch (Exception e) {
            context.setException(e);
            throw e;
        }
    }
}
