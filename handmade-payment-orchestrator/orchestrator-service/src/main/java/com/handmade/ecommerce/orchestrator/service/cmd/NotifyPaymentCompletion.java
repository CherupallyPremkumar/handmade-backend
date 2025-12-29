package com.handmade.ecommerce.orchestrator.service.cmd;

import com.handmade.ecommerce.command.notification.SendNotificationRequest;
import com.handmade.ecommerce.notification.service.NotificationService;
import com.handmade.ecommerce.orchestrator.WebhookExchange;
import com.handmade.ecommerce.paymentorder.PaymentOrderService;
import com.handmade.ecommerce.paymentorder.model.PaymentOrder;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Sends payment completion notifications to buyer and sellers.
 * 
 * Delegates to NotificationService which handles:
 * - Buyer notification (payment successful)
 * - Seller notifications (payment received)
 * - Multiple channels (email, SMS, push, WhatsApp)
 * - Async delivery via message queue
 * - Retry logic
 */
@Component
public class NotifyPaymentCompletion implements Command<WebhookExchange> {

    private static final Logger logger = LoggerFactory.getLogger(NotifyPaymentCompletion.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Override
    public void execute(WebhookExchange context) throws Exception {
        try {
            String paymentId = context.getParsedPayload().getPaymentId();
            List<PaymentOrder> orders = paymentOrderService.findByPaymentId(paymentId);

            if (orders == null || orders.isEmpty()) {
                logger.warn("No payment orders found for payment: {}", paymentId);
                return;
            }

            // Send buyer notification
            sendBuyerNotification(paymentId, orders);

            // Send seller notifications
            for (PaymentOrder order : orders) {
                sendSellerNotification(order);
            }

        } catch (Exception e) {
            // Don't throw - notifications are non-critical
            logger.error("Failed to send payment notifications for payment: {}",
                    context.getParsedPayload().getPaymentId(), e);
        }
    }

    private void sendBuyerNotification(String paymentId, List<PaymentOrder> orders) {
        try {
            double totalAmount = orders.stream()
                    .mapToDouble(o -> o.getAmount().doubleValue())
                    .sum();

            SendNotificationRequest request = new SendNotificationRequest();
            // TODO: Set userId from payment context (need buyer ID)
            // request.setUserId(buyerId);
            request.setTitle("Payment Successful");
            request.setMessage(String.format(
                    "Your payment of ₹%.2f for %d item(s) is confirmed.",
                    totalAmount, orders.size()));
            request.setPriority("HIGH");

            notificationService.sendNotification(request);
            logger.info("Sent buyer notification for payment: {}", paymentId);

        } catch (Exception e) {
            logger.error("Failed to send buyer notification for payment: {}", paymentId, e);
        }
    }

    private void sendSellerNotification(PaymentOrder order) {
        try {
            SendNotificationRequest request = new SendNotificationRequest();
            // TODO: Convert sellerId to Long or update NotificationService to accept String
            // request.setUserId(Long.parseLong(order.getSellerId()));
            request.setTitle("Payment Received");
            request.setMessage(String.format(
                    "₹%s has been credited to your wallet (PENDING). " +
                            "Funds will be available for withdrawal after 7 days.",
                    order.getAmount()));
            request.setPriority("MEDIUM");

            notificationService.sendNotification(request);
            logger.info("Sent seller notification for order: {} to seller: {}",
                    order.getId(), order.getSellerId());

        } catch (Exception e) {
            logger.error("Failed to send seller notification for order: {}", order.getId(), e);
        }
    }
}
