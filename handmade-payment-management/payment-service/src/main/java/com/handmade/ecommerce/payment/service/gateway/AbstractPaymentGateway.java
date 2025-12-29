package com.handmade.ecommerce.payment.service.gateway;

import com.handmade.ecommerce.payment.model.PaymentTransaction;
import java.math.BigDecimal;

/**
 * AbstractPaymentGateway - Base class for all payment gateway implementations
 * Uses Template Method pattern
 */
public abstract class AbstractPaymentGateway {

    /**
     * Get gateway code (e.g., "STRIPE", "RAZORPAY")
     */
    public abstract String getGatewayCode();

    /**
     * Get gateway display name
     */
    public abstract String getGatewayName();

    /**
     * Initialize payment - implemented by each gateway
     */
    protected abstract PaymentInitiationResult doInitiatePayment(
            String orderId,
            BigDecimal amount,
            String currency);

    /**
     * Check payment status - implemented by each gateway
     */
    protected abstract PaymentStatusResult doCheckStatus(String transactionId);

    /**
     * Process refund - implemented by each gateway
     */
    protected abstract RefundResult doProcessRefund(String transactionId, BigDecimal amount);

    /**
     * Validate payment amount - can be overridden
     */
    protected boolean validateAmount(BigDecimal amount) {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Template method - initiates payment with validation
     */
    public final PaymentInitiationResult initiatePayment(
            String orderId,
            BigDecimal amount,
            String currency) {
        // Pre-processing (common logic)
        if (!validateAmount(amount)) {
            throw new IllegalArgumentException("Invalid payment amount");
        }

        if (currency == null || currency.isEmpty()) {
            throw new IllegalArgumentException("Currency required");
        }

        // Call gateway-specific implementation
        PaymentInitiationResult result = doInitiatePayment(orderId, amount, currency);

        // Post-processing (common logic)
        logPaymentInitiation(orderId, amount, currency);

        return result;
    }

    /**
     * Template method - checks payment status
     */
    public final PaymentStatusResult checkPaymentStatus(String transactionId) {
        // Pre-processing
        if (transactionId == null || transactionId.isEmpty()) {
            throw new IllegalArgumentException("Transaction ID required");
        }

        // Call gateway-specific implementation
        PaymentStatusResult result = doCheckStatus(transactionId);

        // Post-processing
        logStatusCheck(transactionId, result.getStatus());

        return result;
    }

    /**
     * Template method - processes refund
     */
    public final RefundResult processRefund(String transactionId, BigDecimal amount) {
        // Validation
        if (!validateAmount(amount)) {
            throw new IllegalArgumentException("Invalid refund amount");
        }

        // Call gateway-specific implementation
        RefundResult result = doProcessRefund(transactionId, amount);

        // Post-processing
        logRefund(transactionId, amount);

        return result;
    }

    /**
     * Common helper - log payment initiation
     */
    protected void logPaymentInitiation(String orderId, BigDecimal amount, String currency) {
        System.out.println(String.format(
                "[%s] Payment initiated for order %s: %s %s",
                getGatewayCode(),
                orderId,
                currency,
                amount));
    }

    /**
     * Common helper - log status check
     */
    protected void logStatusCheck(String transactionId, String status) {
        System.out.println(String.format(
                "[%s] Status check for %s: %s",
                getGatewayCode(),
                transactionId,
                status));
    }

    /**
     * Common helper - log refund
     */
    protected void logRefund(String transactionId, BigDecimal amount) {
        System.out.println(String.format(
                "[%s] Refund processed for %s: %s",
                getGatewayCode(),
                transactionId,
                amount));
    }

    /**
     * Hook method - can be overridden
     */
    protected void onPaymentSuccess(String transactionId) {
        // Default: do nothing
    }

    /**
     * DTOs
     */
    public static class PaymentInitiationResult {
        private String paymentId;
        private String checkoutUrl;
        private String status;

        public PaymentInitiationResult(String paymentId, String checkoutUrl, String status) {
            this.paymentId = paymentId;
            this.checkoutUrl = checkoutUrl;
            this.status = status;
        }

        public String getPaymentId() {
            return paymentId;
        }

        public String getCheckoutUrl() {
            return checkoutUrl;
        }

        public String getStatus() {
            return status;
        }
    }

    public static class PaymentStatusResult {
        private String transactionId;
        private String status;
        private String message;

        public PaymentStatusResult(String transactionId, String status, String message) {
            this.transactionId = transactionId;
            this.status = status;
            this.message = message;
        }

        public String getTransactionId() {
            return transactionId;
        }

        public String getStatus() {
            return status;
        }

        public String getMessage() {
            return message;
        }
    }

    public static class RefundResult {
        private boolean success;
        private String refundId;
        private String message;

        public RefundResult(boolean success, String refundId, String message) {
            this.success = success;
            this.refundId = refundId;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getRefundId() {
            return refundId;
        }

        public String getMessage() {
            return message;
        }
    }
}
