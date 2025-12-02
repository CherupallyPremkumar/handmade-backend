package com.handmade.ecommerce.payment.service;
import com.handmade.ecommerce.command.payment.PaymentResponse;

public interface PaymentService {

    /**
     * Process a payment (may contain multiple payment orders)
     */
    PaymentResponse processPayment(PaymentRequest request);

    /**
     * Get payment status
     */
    PaymentResponse getPaymentStatus(String paymentId);
}