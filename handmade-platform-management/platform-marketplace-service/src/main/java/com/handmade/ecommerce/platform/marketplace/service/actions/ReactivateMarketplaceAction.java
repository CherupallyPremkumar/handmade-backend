package com.handmade.ecommerce.platform.marketplace.service.actions;

import com.handmade.ecommerce.platform.domain.aggregate.Marketplace;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * STM Action to reactivate a suspended marketplace
 * Transitions from SUSPENDED → ACTIVE
 */
public class ReactivateMarketplaceAction extends AbstractSTMTransitionAction<Marketplace, Map<String, String>> {
    
    private static final Logger logger = LoggerFactory.getLogger(ReactivateMarketplaceAction.class);
    
    @Override
    public void transitionTo(Marketplace marketplace, 
                            Map<String, String> payload, 
                            State startState, 
                            String eventId, 
                            State endState, 
                            STMInternalTransitionInvoker<?> stm, 
                            Transition transition) throws Exception {
        
        String reactivatedBy = payload != null ? payload.getOrDefault("reactivatedBy", "SYSTEM") : "SYSTEM";
        
        // Reactivate marketplace
        marketplace.activate(reactivatedBy);
        
        logger.info("Marketplace reactivated: {} by {}", marketplace.getMarketplaceId(), reactivatedBy);
    }
}
