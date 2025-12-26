package com.handmade.ecommerce.orchestrator.service;

import com.handmade.ecommerce.command.CreatePaymentRequest;
import com.handmade.ecommerce.command.PaymentResponse;
import com.handmade.ecommerce.orchestrator.PaymentExchange;
import com.handmade.ecommerce.orchestrator.WebhookExchange;

public interface ProductOrchestratorService {
   PaymentExchange createPayment(CreatePaymentRequest request);

   WebhookExchange processWebhook(String request);
}
