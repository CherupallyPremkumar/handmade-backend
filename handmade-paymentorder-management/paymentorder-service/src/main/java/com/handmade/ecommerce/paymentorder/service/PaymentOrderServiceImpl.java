package com.handmade.ecommerce.paymentorder.service;

import com.handmade.ecommerce.paymentorder.PaymentOrderService;
import com.handmade.ecommerce.paymentorder.model.PaymentOrder;
import org.chenile.stm.STM;
import org.chenile.stm.impl.STMActionsInfoProvider;
import org.chenile.utils.entity.service.EntityStore;
import org.chenile.workflow.service.impl.StateEntityServiceImpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentOrderServiceImpl extends StateEntityServiceImpl<PaymentOrder> implements PaymentOrderService {
    public PaymentOrderServiceImpl(STM<PaymentOrder> stm, STMActionsInfoProvider stmActionsInfoProvider,
            EntityStore<PaymentOrder> entityStore) {
        super(stm, stmActionsInfoProvider, entityStore);
    }

    @Override
    public void createBatch(List<PaymentOrder> orders) {
        if (orders == null || orders.isEmpty()) {
            return;
        }

        // Group orders by sellerId to combine multiple items from same seller
        Map<String, PaymentOrder> ordersBySeller = new HashMap<>();

        for (PaymentOrder order : orders) {
            String sellerId = order.getSellerId();

            if (ordersBySeller.containsKey(sellerId)) {
                // Seller already has an order - combine amounts
                PaymentOrder existingOrder = ordersBySeller.get(sellerId);
                BigDecimal combinedAmount = existingOrder.getAmount().add(order.getAmount());
                existingOrder.setAmount(combinedAmount);
            } else {
                // First order for this seller
                ordersBySeller.put(sellerId, order);
            }
        }

        // Create one PaymentOrder per seller
        for (PaymentOrder combinedOrder : ordersBySeller.values()) {
            create(combinedOrder);
        }
    }

    @Override
    public void markReadyForDistribution(String paymentId) {
        if (paymentId == null || paymentId.isEmpty()) {
            throw new IllegalArgumentException("Payment ID cannot be null or empty");
        }

        List<PaymentOrder> orders = findByPaymentId(paymentId);
        if (orders == null || orders.isEmpty()) {
            return;
        }

        // Update each order's status to READY_FOR_DISTRIBUTION
        for (PaymentOrder order : orders) {
            // Use state machine to transition to READY_FOR_DISTRIBUTION
            // The event name should match your paymentorder-states.xml
            processById(order.getId(), "mark_ready", null);
        }
    }

    @Override
    public List<PaymentOrder> findByPaymentId(String paymentId) {
        return List.of();
    }
}
