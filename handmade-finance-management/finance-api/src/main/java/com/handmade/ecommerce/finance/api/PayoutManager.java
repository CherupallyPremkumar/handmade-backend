package com.handmade.ecommerce.finance.api;

import com.handmade.ecommerce.finance.domain.aggregate.PayoutInstruction;
import org.chenile.workflow.api.StateEntityService;
import java.math.BigDecimal;

/**
 * Service to orchestrate seller payouts.
 * Manages the transition from platform funds to bank transfers.
 */
public interface PayoutManager extends StateEntityService<PayoutInstruction> {
    
    /**
     * Triggers a new payout request for a seller
     */
    void requestPayout(String sellerId, BigDecimal amount);
}
