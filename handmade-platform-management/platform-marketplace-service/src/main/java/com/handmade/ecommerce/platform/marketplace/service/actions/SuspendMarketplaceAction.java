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
 * STM Action to suspend a marketplace
 * Transitions from ACTIVE → SUSPENDED
 */
public class SuspendMarketplaceAction extends AbstractSTMTransitionAction<Marketplace, Map<String, String>> {
    
    private static final Logger logger = LoggerFactory.getLogger(SuspendMarketplaceAction.class);
    
    @Override
    public void transitionTo(Marketplace marketplace, 
                            Map<String, String> payload, 
                            State startState, 
                            String eventId, 
                            State endState, 
                            STMInternalTransitionInvoker<?> stm, 
                            Transition transition) throws Exception {
        
        String suspendedBy = payload != null ? payload.getOrDefault("suspendedBy", "SYSTEM") : "SYSTEM";
        String reason = payload != null ? payload.getOrDefault("reason", "No reason provided") : "No reason provided";
        
        // Suspend marketplace
        marketplace.suspend(suspendedBy, reason);
        
        logger.info("Marketplace suspended: {} by {} - Reason: {}", 
                   marketplace.getMarketplaceId(), suspendedBy, reason);
    }
}
