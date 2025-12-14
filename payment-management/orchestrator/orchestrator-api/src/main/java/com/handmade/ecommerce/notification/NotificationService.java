package com.handmade.ecommerce.notification;

import com.handmade.ecommerce.paymentorder.model.PaymentOrder;

import java.util.List;

/**
 * Notification service for sending payment-related notifications.
 * 
 * Handles:
 * - Buyer notifications (payment success, order updates)
 * - Seller notifications (payment received, payout completed)
 * - Multiple channels (email, SMS, push, WhatsApp)
 * - Async delivery via message queue (MQTT/Kafka)
 * - Retry logic for failed deliveries
 * 
 * Implementation should:
 * - Use message queue for async delivery
 * - Not block caller
 * - Handle failures gracefully
 * - Log all notification attempts
 */
public interface NotificationService {

    /**
     * Notify buyer and sellers about successful payment
     * 
     * @param paymentId Payment ID
     * @param orders    List of payment orders (one per seller)
     */
    void notifyPaymentSuccess(String paymentId, List<PaymentOrder> orders);

    /**
     * Notify seller about payout completion
     * 
     * @param sellerId    Seller ID
     * @param amount      Payout amount
     * @param currency    Currency code
     * @param referenceId Payout reference ID
     */
    void notifyPayoutCompleted(String sellerId, String amount, String currency, String referenceId);

    /**
     * Notify seller about payout failure
     * 
     * @param sellerId Seller ID
     * @param amount   Payout amount
     * @param reason   Failure reason
     */
    void notifyPayoutFailed(String sellerId, String amount, String reason);
}
