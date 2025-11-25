package com.handmade.ecommerce.payment.service.gateway;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;

/**
 * RazorpayPaymentGateway - Razorpay implementation
 */
@Service
public class RazorpayPaymentGateway extends AbstractPaymentGateway {

    @Override
    public String getGatewayCode() {
        return "RAZORPAY";
    }

    @Override
    public String getGatewayName() {
        return "Razorpay";
    }

    @Override
    protected PaymentInitiationResult doInitiatePayment(
            String orderId,
            BigDecimal amount,
            String currency) {
        // TODO: Call Razorpay API
        // Convert amount to paise (smallest currency unit)
        long amountInPaise = amount.multiply(new BigDecimal("100")).longValue();

        // Mock implementation
        String paymentId = "order_" + System.currentTimeMillis();
        String checkoutUrl = "https://api.razorpay.com/v1/checkout/" + paymentId;

        System.out.println("Razorpay: Creating order for " + orderId);

        return new PaymentInitiationResult(paymentId, checkoutUrl, "CREATED");
    }

    @Override
    protected PaymentStatusResult doCheckStatus(String transactionId) {
        // TODO: Call Razorpay API
        return new PaymentStatusResult(
                transactionId,
                "CAPTURED",
                "Payment captured successfully");
    }

    @Override
    protected RefundResult doProcessRefund(String transactionId, BigDecimal amount) {
        // TODO: Call Razorpay refund API
        String refundId = "rfnd_" + System.currentTimeMillis();
        return new RefundResult(true, refundId, "Refund initiated");
    }
}
