package com.handmade.ecommerce.orchestrator.service;


import com.handmade.ecommerce.command.CreatePaymentRequest;
import com.handmade.ecommerce.command.PaymentResponse;

/**
 * Payment Processing Service Interface
 * Main orchestrator for payment flow
 *
 */
public interface PaymentProcessingService {

    PaymentResponse processPayment(CreatePaymentRequest request);

    PaymentResponse getPaymentStatus(String paymentOrderId);
}
