package com.handmade.ecommerce.finance.service.listener;

import com.handmade.ecommerce.finance.api.SettlementManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

/**
 * Listener to capture order completion events and trigger settlement.
 * In a real system, this would use @EventListener or a Queue listener (Kafka/Sqs).
 * For this implementation, we provide a unified entry point for orchestration.
 */
@Component
public class SettlementListener {

    @Autowired
    private SettlementManager settlementManager;

    /**
     * Handles order completion.
     * Maps Order details to Finance Ledger credits.
     */
    public void onOrderCompleted(String orderId, String sellerId, BigDecimal amount, String currency) {
        // Trigger the fiscal settlement
        settlementManager.settleOrder(sellerId, orderId, amount, currency);
    }
}
