package com.handmade.ecommerce.payment.service;


import com.handmade.ecommerce.command.dto.payment.CreatePaymentRequest;
import com.handmade.ecommerce.command.dto.payment.PaymentResponse;

/**
 * Payment Service Interface
 * Used by both implementation and Chenile Proxy
 */
public interface PaymentService {

    /**
     * Create a new payment
     * 
     * @param request Payment creation request
     * @return Payment response with transaction details
     */
    PaymentResponse createPayment(CreatePaymentRequest request);

    /**
     * Get payment by ID
     * 
     * @param paymentId Payment identifier
     * @return Payment details
     */
    PaymentResponse getPayment(Long paymentId);

    /**
     * Refund a payment
     * 
     * @param paymentId Payment to refund
     * @param reason    Refund reason
     */
    void refundPayment(Long paymentId, String reason);
}
