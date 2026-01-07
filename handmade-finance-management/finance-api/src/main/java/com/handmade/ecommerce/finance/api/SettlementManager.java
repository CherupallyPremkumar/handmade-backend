package com.handmade.ecommerce.finance.api;

import com.handmade.ecommerce.finance.domain.aggregate.SettlementAccount;
import org.chenile.workflow.api.StateEntityService;
import java.math.BigDecimal;

/**
 * Service to manage Marketplace Settlements and Ledger movements
 */
public interface SettlementManager extends StateEntityService<SettlementAccount> {
    
    /**
     * Records a successful order and calculates platform fees
     */
    void settleOrder(String sellerId, String orderId, BigDecimal amount, String currency);

    /**
     * Periodically releases funds from PENDING to AVAILABLE
     */
    void releaseEscrowFunds(String sellerId, BigDecimal amount);
}
