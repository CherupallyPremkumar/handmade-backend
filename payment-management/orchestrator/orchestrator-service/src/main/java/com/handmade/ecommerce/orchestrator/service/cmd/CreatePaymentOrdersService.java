package com.handmade.ecommerce.orchestrator.service.cmd;

import com.handmade.ecommerce.command.PaymentOrderRequest;
import com.handmade.ecommerce.orchestrator.PaymentExchange;
import com.handmade.ecommerce.paymentorder.PaymentOrderService;
import com.handmade.ecommerce.paymentorder.model.PaymentOrder;
import com.handmade.ecommerce.paymentorder.service.PaymentOrderService;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates PaymentOrder entities for each seller in the cart.
 * 
 * In aggregated payment model:
 * - User pays platform once (total amount)
 * - PaymentOrders track how much each seller should receive
 * - One PaymentOrder per seller
 * 
 * Example:
 * Cart with 3 sellers:
 * - Seller A: ₹500
 * - Seller B: ₹300
 * - Seller C: ₹200
 * 
 * Creates 3 PaymentOrder entities (one per seller)
 * 
 * Each PaymentOrder stores:
 * - paymentOrderId (unique identifier)
 * - paymentId (reference to parent Payment)
 * - sellerId (which seller gets this money)
 * - amount (how much seller should receive)
 * - status (NOT_STARTED → will be updated after PSP confirmation)
 */
@Component
public class CreatePaymentOrdersService implements Command<PaymentExchange> {

    private static final Logger log = LoggerFactory.getLogger(CreatePaymentOrdersService.class);

    @Autowired
    private PaymentOrderService paymentOrderService;

    @Override
    public void execute(PaymentExchange context) throws Exception {
        try {
            String paymentId = context.getResponse().getPaymentId();
            List<PaymentOrderRequest> orderRequests = context.getRequest().getPaymentOrders();

            log.info("Creating {} PaymentOrders for Payment: {}",
                    orderRequests.size(), paymentId);

            List<PaymentOrder> orders = new ArrayList<>();
            for (PaymentOrderRequest orderReq : orderRequests) {
                PaymentOrder order = new PaymentOrder();
                order.setId(orderReq.getPaymentOrderId());
                order.setPaymentId(paymentId);
                order.setSellerId(orderReq.getSellerId());
                order.setAmount(orderReq.getAmount());
                order.setCurrency(orderReq.getCurrency());
                order.setCurrentState();
                orders.add(order);
            }

            paymentOrderService.createBatch(orders);

            log.info("Successfully created {} PaymentOrders for Payment: {} in batch",
                    orders.size(), paymentId);

        } catch (Exception e) {
            log.error("Failed to create PaymentOrders for Payment: {}",
                    context.getResponse().getPaymentId(), e);
            context.setException(e);
            throw e;
        }
    }
}
