package com.handmade.ecommerce.payment.service;

import com.handmade.ecommerce.command.CreatePaymentRequest;
import com.handmade.ecommerce.command.PaymentResponse;
import com.handmade.ecommerce.payment.model.Payment;
import com.handmade.ecommerce.payment.model.PaymentSession;
import com.handmade.ecommerce.command.WebhookPayload;

public interface PaymentService {


    PaymentResponse processPayment(CreatePaymentRequest request);

    PaymentResponse getPaymentStatus(String paymentId);

    Payment findByIdempotencyKey(String idempotencyKey);

    void addCheckoutSession(PaymentSession session);

    void updatePayment(WebhookPayload webhookPayload);

}