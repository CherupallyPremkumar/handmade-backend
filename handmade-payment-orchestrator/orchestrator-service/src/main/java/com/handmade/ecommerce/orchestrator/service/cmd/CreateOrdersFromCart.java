package com.handmade.ecommerce.orchestrator.service.cmd;

import com.handmade.ecommerce.cart.service.CartService;
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
 * Creates orders from cart after successful payment.
 * 
 * Flow:
 * 1. Payment webhook received
 * 2. Payment confirmed
 * 3. Create orders from cart items
 * 4. Clear cart
 * 
 * This runs SYNCHRONOUSLY in webhook chain to ensure:
 * - Orders are created immediately
 * - Customer sees order confirmation
 * - Sellers know what to ship
 * 
 * Note: Actual order creation delegated to OrderService
 * For now, just clears cart after payment success
 */
@Component
public class CreateOrdersFromCart implements Command<WebhookExchange> {

    private static final Logger logger = LoggerFactory.getLogger(CreateOrdersFromCart.class);

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Autowired(required = false)
    private CartService cartService;

    // TODO: Add OrderService when implemented
    // @Autowired
    // private OrderService orderService;

    @Override
    public void execute(WebhookExchange context) throws Exception {
        try {
            String paymentId = context.getParsedPayload().getPaymentId();
            List<PaymentOrder> paymentOrders = paymentOrderService.findByPaymentId(paymentId);

            if (paymentOrders == null || paymentOrders.isEmpty()) {
                logger.warn("No payment orders found for payment: {}", paymentId);
                return;
            }

            // TODO: Implement order creation
            // For each PaymentOrder:
            // 1. Create Order from cart items
            // 2. Link Order to PaymentOrder
            // 3. Update inventory
            // 4. Notify seller to ship

            // For now, just clear cart if CartService is available
            if (cartService != null) {
                // Get cartId from first PaymentOrder (assuming all from same cart)
                // String cartId = paymentOrders.get(0).getCartId();
                // if (cartId != null) {
                // cartService.clearCart(cartId);
                // logger.info("Cleared cart: {} after payment: {}", cartId, paymentId);
                // }
            }

            logger.info("Order creation placeholder executed for payment: {}", paymentId);

        } catch (Exception e) {
            // This is CRITICAL - if order creation fails, payment should fail
            context.setException(e);
            logger.error("Failed to create orders for payment: {}",
                    context.getParsedPayload().getPaymentId(), e);
            throw e; // Throw to rollback transaction
        }
    }
}
