package com.handmade.ecommerce.orchestrator.service.impl;

import com.handmade.ecommerce.command.CreatePaymentRequest;
import com.handmade.ecommerce.orchestrator.PaymentExchange;
import com.handmade.ecommerce.orchestrator.WebhookExchange;
import com.handmade.ecommerce.orchestrator.service.context.PaymentEntryPoint;
import com.handmade.ecommerce.orchestrator.service.context.WebhookEntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.handmade.ecommerce.orchestrator.service.ProductOrchestratorService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductOrchestratorServiceImpl implements ProductOrchestratorService {
    @Autowired
    PaymentEntryPoint paymentEntryPoint;
    @Autowired
    WebhookEntryPoint webhookEntryPoint;

    private static final Logger log = LoggerFactory.getLogger(ProductOrchestratorServiceImpl.class);

    @Override
    @Transactional
    public PaymentExchange createPayment(CreatePaymentRequest exchange) {

        try {
            PaymentExchange paymentExchange=new PaymentExchange();
            paymentExchange.setRequest(exchange);
            return paymentEntryPoint.execute(paymentExchange);
        } catch (Exception e) {
            log.error("Error executing payment orchestration", e);
            throw new RuntimeException("Payment orchestration failed", e);
        }
    }

    @Override
    @Transactional
    public WebhookExchange processWebhook(String exchange) {
        try {
            WebhookExchange webhookExchange=new WebhookExchange();
            webhookExchange.setRequest(exchange);
            return webhookEntryPoint.execute(webhookExchange);
        } catch (Exception e) {
            log.error("Error executing webhook orchestration", e);
            throw new RuntimeException("Webhook orchestration failed", e);
        }
    }
}
