package com.handmade.ecommerce.orchestrator.service.cmd;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.ecommerce.command.WebhookRequest;
import com.handmade.ecommerce.orchestrator.WebhookExchange;
import com.handmade.ecommerce.paymentexecutor.PaymentExecutor;
import com.handmade.ecommerce.paymentexecutor.PaymentExecutorFactory;
import org.chenile.owiz.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Verifies webhook signature from PSP to ensure authenticity.
 * Converts String JSON to WebhookRequest and verifies signature using
 * PaymentExecutor.
 */
@Component
public class VerifyWebhookSignature implements Command<WebhookExchange> {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PaymentExecutorFactory paymentExecutorFactory;

    @Override
    public void execute(WebhookExchange context) throws Exception {
        try {
            // 1. Convert String JSON to WebhookRequest
            String requestJson = context.getRequest();
            WebhookRequest request = objectMapper.readValue(requestJson, WebhookRequest.class);

            // 2. Verify signature using PSP-specific executor
            String pspType = request.getPspType();
            String payload = request.getPayload();
            String receivedSignature = request.getSignature();

            PaymentExecutor executor = paymentExecutorFactory.getExecutor(pspType);
            if (executor == null) {
                throw new IllegalArgumentException("No executor found for PSP type: " + pspType);
            }

            boolean isValid = executor.verifyWebhookSignature(payload, receivedSignature);

            if (!isValid) {
                throw new SecurityException("Invalid webhook signature for PSP: " + pspType);
            }

            // 3. Set WebhookRequest in context for next command
            context.setConvertedRequest(request);

        } catch (Exception e) {
            context.setException(e);
            throw e;
        }
    }
}
