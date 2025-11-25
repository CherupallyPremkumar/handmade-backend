package com.handmade.ecommerce.payment.core;

import java.math.BigDecimal;

/**
 * PaymentGatewayPlugin - Interface that all payment gateway plugins must implement
 * This enables true plugin architecture - each gateway is a separate module
 */
public interface PaymentGatewayPlugin {
    
    /**
     * Get unique gateway code (e.g., "STRIPE", "RAZORPAY", "PAYPAL")
     */
    String getGatewayCode();
    
    /**
     * Get human-readable gateway name
     */
    String getGatewayName();
    
    /**
     * Get gateway description
     */
    String getDescription();
    
    /**
     * Check if this gateway is enabled
     */
    boolean isEnabled();
    
    /**
     * Initiate payment and return checkout URL
     * 
     * @param orderId Order ID
     * @param amount Payment amount
     * @param currency Currency code (USD, INR, EUR)
     * @return Payment initiation result
     */
    PaymentInitiationResult initiatePayment(String orderId, BigDecimal amount, String currency);
    
    /**
     * Check payment status
     * 
     * @param transactionId Transaction ID
     * @return Payment status
     */
    PaymentStatusResult checkPaymentStatus(String transactionId);
    
    /**
     * Process refund
     * 
     * @param transactionId Transaction ID
     * @param amount Refund amount
     * @return Refund result
     */
    RefundResult processRefund(String transactionId, BigDecimal amount);
    
    /**
     * Verify webhook signature
     * 
     * @param payload Webhook payload
     * @param signature Signature from gateway
     * @return true if signature is valid
     */
    boolean verifyWebhookSignature(String payload, String signature);
    
    /**
     * Get supported currencies
     */
    String[] getSupportedCurrencies();
    
    /**
     * Get minimum transaction amount
     */
    BigDecimal getMinimumAmount(String currency);
    
    /**
     * DTOs
     */
    class PaymentInitiationResult {
        private String paymentId;
        private String checkoutUrl;
        private String status;
        
        public PaymentInitiationResult(String paymentId, String checkoutUrl, String status) {
            this.paymentId = paymentId;
            this.checkoutUrl = checkoutUrl;
            this.status = status;
        }
        
        public String getPaymentId() { return paymentId; }
        public String getCheckoutUrl() { return checkoutUrl; }
        public String getStatus() { return status; }
    }
    
    class PaymentStatusResult {
        private String transactionId;
        private String status;
        private String message;
        
        public PaymentStatusResult(String transactionId, String status, String message) {
            this.transactionId = transactionId;
            this.status = status;
            this.message = message;
        }
        
        public String getTransactionId() { return transactionId; }
        public String getStatus() { return status; }
        public String getMessage() { return message; }
    }
    
    class RefundResult {
        private boolean success;
        private String refundId;
        private String message;
        
        public RefundResult(boolean success, String refundId, String message) {
            this.success = success;
            this.refundId = refundId;
            this.message = message;
        }
        
        public boolean isSuccess() { return success; }
        public String getRefundId() { return refundId; }
        public String getMessage() { return message; }
    }
}
