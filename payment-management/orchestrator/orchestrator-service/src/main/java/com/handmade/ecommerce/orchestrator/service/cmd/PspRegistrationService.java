package com.handmade.ecommerce.orchestrator.service.cmd;

import com.handmade.ecommerce.command.CheckoutSessionResponse;
import com.handmade.ecommerce.command.CreatePaymentRequest;
import com.handmade.ecommerce.orchestrator.PaymentExchange;
import com.handmade.ecommerce.paymentexecutor.PaymentExecutor;
import com.handmade.ecommerce.paymentexecutor.PaymentExecutorFactory;
import com.handmade.ecommerce.paymentexecutor.model.CheckoutSession;
import com.handmade.ecommerce.paymentexecutor.model.CreateCheckoutRequest;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PspRegistrationService implements Command<PaymentExchange> {

    private static final Logger log = LoggerFactory.getLogger(PspRegistrationService.class);

    @Autowired
    private PaymentExecutorFactory paymentExecutorFactory;

    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    public void execute(PaymentExchange context) throws Exception {
        try {
            String paymentId = context.getResponse().getPaymentId();

            log.info("Registering payment with PSP. PaymentId: {}, Amount: {}",
                    paymentId, context.getRequest().getTotalAmount());

            // Determine PSP based on region/currency
            String pspType = paymentExecutorFactory.determinePspType(context.getRequest().getCurrency());

            // Get appropriate PSP executor
            PaymentExecutor executor = paymentExecutorFactory.getExecutor(pspType);

            // Build checkout session request
            CreateCheckoutRequest sessionRequest = buildCheckoutSessionRequest(context);

            // Call PSP to create checkout session
            CheckoutSession session = executor.createCheckoutSession(sessionRequest);

            // Store session details in context
            CheckoutSessionResponse sessionResponse = new CheckoutSessionResponse();
            sessionResponse.setPaymentOrderId(paymentId);
            sessionResponse.setCheckoutUrl(session.getCheckoutUrl());
            sessionResponse.setSessionId(session.getSessionId());
            sessionResponse.setPspType(pspType);

            context.setCheckoutSessionResponse(sessionResponse);

            log.info("Successfully created PSP checkout session. PaymentId: {}, PSP: {}, SessionId: {}, URL: {}",
                    paymentId, pspType, session.getSessionId(), session.getCheckoutUrl());

        } catch (Exception e) {
            log.error("Failed to register payment with PSP. PaymentId: {}",
                    context.getResponse().getPaymentId(), e);
            context.setException(e);
            throw e;
        }
    }

    /**
     * Builds checkout session request for PSP.
     * 
     * @param context Payment context
     * @return Checkout session request
     */
    private CreateCheckoutRequest buildCheckoutSessionRequest(PaymentExchange context) {
        String paymentId = context.getResponse().getPaymentId();
        CreatePaymentRequest paymentRequest = context.getRequest();

        CreateCheckoutRequest request = new CreateCheckoutRequest();

        // Payment details
        request.setPaymentId(paymentId);
        request.setAmount(paymentRequest.getTotalAmount());
        request.setCurrency(paymentRequest.getCurrency());

        // Customer details
        request.setBuyerId(paymentRequest.getBuyerId());

        // Callback URLs
        request.setSuccessUrl(buildUrl("/payment/success", paymentId));
        request.setCancelUrl(buildUrl("/payment/cancel", paymentId));
        request.setWebhookUrl(baseUrl + "/api/webhooks/payment");

        // Metadata for tracking
        request.addMetadata("paymentId", paymentId);
        request.addMetadata("buyerId", paymentRequest.getBuyerId());
        request.addMetadata("checkoutId", paymentRequest.getCheckoutId());

        return request;
    }

    /**
     * Helper to build callback URLs.
     */
    private String buildUrl(String path, String paymentId) {
        return baseUrl + path + "?paymentId=" + paymentId;
    }
}
