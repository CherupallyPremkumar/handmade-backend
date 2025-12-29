package com.handmade.ecommerce.paymentexecutor;

import com.handmade.ecommerce.paymentexecutor.model.CheckoutSession;
import com.handmade.ecommerce.paymentexecutor.model.CreateCheckoutRequest;
import com.handmade.ecommerce.paymentexecutor.model.PaymentStatus;
import com.handmade.ecommerce.paymentexecutor.model.RefundRequest;
import com.handmade.ecommerce.paymentexecutor.model.RefundResponse;
import com.handmade.ecommerce.paymentexecutor.model.WebhookPayload;

import java.math.BigDecimal;

/**
 * PaymentExecutor Interface
 * 
 * Strategy interface for executing payment operations with different PSPs
 * (Payment Service Providers).
 * Implementations include RazorpayExecutor, StripeExecutor, PayPalExecutor,
 * etc.
 * 
 * This interface abstracts PSP-specific logic and allows the orchestrator to
 * work with
 * any PSP without knowing the implementation details.
 * 
 * @author Handmade E-Commerce Team
 */
public interface PaymentExecutor {

    /**
     * Create a checkout session with the PSP.
     *
     * @param request Checkout session request
     * @return CheckoutSession containing sessionId and checkoutUrl
     * @throws PaymentExecutorException if creation fails
     */
    CheckoutSession createCheckoutSession(CreateCheckoutRequest request);

    /**
     * Check the current status of a payment with the PSP.
     * 
     * Used for polling payment status or reconciliation.
     * 
     * @param pspPaymentId PSP's payment identifier
     * @return Current payment status
     * @throws PaymentExecutorException if status check fails
     */
    PaymentStatus checkPaymentStatus(String pspPaymentId);

    /**
     * Initiate a refund for a previously successful payment.
     * 
     * @param refundRequest Refund request details
     * @return RefundResponse containing refund status and refund ID
     * @throws PaymentExecutorException if refund initiation fails
     */
    RefundResponse initiateRefund(RefundRequest refundRequest);

    /**
     * Check the status of a refund.
     * 
     * @param pspRefundId PSP's refund identifier
     * @return Current refund status
     * @throws PaymentExecutorException if refund status check fails
     */
    PaymentStatus checkRefundStatus(String pspRefundId);

    /**
     * Capture a payment that was previously authorized.
     * 
     * Some PSPs support a two-step process: authorize first, then capture later.
     * This method captures a previously authorized payment.
     * 
     * @param pspPaymentId PSP's payment identifier
     * @param amount       Amount to capture (can be less than authorized amount)
     * @return Payment status after capture
     * @throws PaymentExecutorException if capture fails
     */
    PaymentStatus capturePayment(String pspPaymentId, BigDecimal amount);

    /**
     * Cancel a payment that is pending or authorized but not yet captured.
     * 
     * @param pspPaymentId PSP's payment identifier
     * @return Payment status after cancellation
     * @throws PaymentExecutorException if cancellation fails
     */
    PaymentStatus cancelPayment(String pspPaymentId);

    /**
     * Validate webhook signature to ensure the webhook is from the legitimate PSP.
     * 
     * @param payload   Webhook payload
     * @param signature Signature from PSP
     * @return true if signature is valid, false otherwise
     */
    boolean verifyWebhookSignature(String payload, String signature);

    /**
     * Parse webhook payload into normalized format.
     * 
     * @param payload Raw webhook payload string
     * @return Normalized WebhookPayload
     */
    WebhookPayload parseWebhookPayload(String payload);

    /**
     * Get the PSP type this executor handles.
     * 
     * @return PSP type (e.g., "RAZORPAY", "STRIPE", "PAYPAL")
     */
    String getPspType();
}
