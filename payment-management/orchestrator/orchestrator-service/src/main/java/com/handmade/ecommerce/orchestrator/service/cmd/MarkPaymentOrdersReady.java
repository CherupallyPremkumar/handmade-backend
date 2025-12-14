package com.handmade.ecommerce.orchestrator.service.cmd;

import com.handmade.ecommerce.orchestrator.WebhookExchange;

import com.handmade.ecommerce.paymentorder.PaymentOrderService;
import org.chenile.owiz.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Marks PaymentOrders as ready for distribution.
 * 
 * IMPORTANT: This command does NOT actually transfer money to sellers!
 * It only marks PaymentOrders as "READY_FOR_DISTRIBUTION".
 * 
 * Why NOT distribute immediately on webhook?
 * 1. Refund risk - Customer might request refund within 7 days
 * 2. Chargeback risk - Disputes can happen within 30-120 days
 * 3. Order fulfillment - Seller might not ship the product
 * 4. Settlement delay - PSP takes 2-7 days to settle funds
 * 
 * Actual distribution happens later via:
 * - Scheduled payout job (after 7-day hold period)
 * - After order delivery confirmation
 * - Manual payout trigger by admin
 * 
 * This command:
 * 1. Gets PaymentOrders for this payment
 * 2. Marks them as READY_FOR_DISTRIBUTION
 * 3. Actual money transfer happens in separate payout flow
 */
@Component
public class MarkPaymentOrdersReady implements Command<WebhookExchange> {

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Override
    public void execute(WebhookExchange context) throws Exception {
        try {
            // Get payment ID from webhook payload
            String paymentId = context.getParsedPayload().getPaymentId();

            // Mark all PaymentOrders for this payment as ready for distribution
            // The service will:
            // 1. Find all PaymentOrders for this paymentId
            // 2. Update their status to "READY_FOR_DISTRIBUTION"
            // 3. Save the updates
            paymentOrderService.markReadyForDistribution(paymentId);

            // Actual distribution happens later via:
            // 1. Scheduled job (after 7-day hold)
            // 2. After delivery confirmation
            // 3. Manual admin trigger

        } catch (Exception e) {
            context.setException(e);
            throw e;
        }
    }
}
