package com.handmade.ecommerce.payment.service;

import com.handmade.ecommerce.payment.dto.CreatePaymentRequest;
import com.handmade.ecommerce.payment.dto.PaymentResponse;
import com.handmade.ecommerce.payment.dto.WebhookPayload;
import com.handmade.ecommerce.payment.model.Payment;
import com.handmade.ecommerce.payment.model.PaymentSession;

public interface PaymentService {

    PaymentResponse processPayment(CreatePaymentRequest request);

    PaymentResponse getPaymentStatus(String paymentId);

    Payment findByIdempotencyKey(String idempotencyKey);

    void addCheckoutSession(PaymentSession session);

    void updatePayment(WebhookPayload webhookPayload);

}