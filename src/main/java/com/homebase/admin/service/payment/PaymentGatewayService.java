package com.homebase.admin.service.payment;

import com.homebase.admin.dto.CreatePaymentRequest;
import com.homebase.admin.dto.CreatePaymentResponse;

/**
 * Payment Gateway Service Interface
 * Allows switching between different payment gateways (PhonePe, Razorpay, Stripe, etc.)
 */
public interface PaymentGatewayService {
    
    /**
     * Initiate payment with the gateway
     * @param request Payment request details
     * @return Payment response with payment URL and transaction ID
     */
    CreatePaymentResponse initiatePayment(CreatePaymentRequest request);
    
    /**
     * Verify payment status with the gateway
     * @param transactionId Transaction ID to verify
     * @return Payment verification result
     */
    PaymentVerificationResult verifyPayment(String transactionId);
    
    /**
     * Handle payment callback/webhook from gateway
     * @param callbackData Raw callback data from gateway
     * @return Processed callback result
     */
    PaymentCallbackResult handleCallback(String callbackData);
    
    /**
     * Get payment gateway name
     */
    String getGatewayName();
}
