package com.handmade.ecommerce.payment.service.gateway;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;

/**
 * StripePaymentGateway - Stripe implementation
 */
@Service
public class StripePaymentGateway extends AbstractPaymentGateway {

    @Override
    public String getGatewayCode() {
        return "STRIPE";
    }

    @Override
    public String getGatewayName() {
        return "Stripe";
    }

    @Override
    protected PaymentInitiationResult doInitiatePayment(
            String orderId,
            BigDecimal amount,
            String currency) {
        // TODO: Call Stripe API
        // StripeClient stripe = new StripeClient(apiKey);
        // PaymentIntent intent = stripe.createPaymentIntent(amount, currency);

        // Mock implementation
        String paymentId = "pi_" + System.currentTimeMillis();
        String checkoutUrl = "https://checkout.stripe.com/pay/" + paymentId;

        System.out.println("Stripe: Creating payment intent for order " + orderId);

        return new PaymentInitiationResult(paymentId, checkoutUrl, "PENDING");
    }

    @Override
    protected PaymentStatusResult doCheckStatus(String transactionId) {
        // TODO: Call Stripe API
        // PaymentIntent intent = stripe.retrievePaymentIntent(transactionId);

        // Mock implementation
        return new PaymentStatusResult(
                transactionId,
                "SUCCEEDED",
                "Payment completed successfully");
    }

    @Override
    protected RefundResult doProcessRefund(String transactionId, BigDecimal amount) {
        // TODO: Call Stripe refund API
        // Refund refund = stripe.createRefund(transactionId, amount);

        // Mock implementation
        String refundId = "re_" + System.currentTimeMillis();
        return new RefundResult(true, refundId, "Refund processed");
    }

    @Override
    protected void onPaymentSuccess(String transactionId) {
        // Stripe-specific: Send receipt email
        System.out.println("Stripe: Sending receipt for " + transactionId);
    }
}
