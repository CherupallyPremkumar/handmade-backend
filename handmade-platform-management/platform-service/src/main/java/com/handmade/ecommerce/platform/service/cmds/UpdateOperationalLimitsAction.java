package com.handmade.ecommerce.platform.service.cmds;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.domain.valueobject.OperationalLimits;
import com.handmade.ecommerce.platform.dto.UpdateOperationalLimitsPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action to update platform operational limits
 */
public class UpdateOperationalLimitsAction extends AbstractSTMTransitionAction<PlatformOwner, UpdateOperationalLimitsPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(UpdateOperationalLimitsAction.class);
    
    @Override
    public void transitionTo(PlatformOwner platform,
                            UpdateOperationalLimitsPayload payload,
                            State startState,
                            String eventId,
                            State endState,
                            STMInternalTransitionInvoker<?> stm,
                            Transition transition) throws Exception {
        
        logger.info("Updating operational limits for platform: {} by: {}", 
                   platform.id, payload.getUpdatedBy());
        
        // Get current limits or create new
        OperationalLimits current = platform.operationalLimits;
        if (current == null) {
            current = new OperationalLimits();
        }
        
        // Update limits (preserve existing if not provided)
        if (payload.getMaxSellersPerDay() != null) {
            current.setMaxSellersPerDay(payload.getMaxSellersPerDay());
        }
            
        if (payload.getMaxTransactionAmount() != null) {
            current.setMaxTransactionAmount(payload.getMaxTransactionAmount());
        }
            
        if (payload.getGlobalRateLimit() != null) {
            current.setGlobalRateLimit(payload.getGlobalRateLimit());
        }
        
        platform.operationalLimits = current;
        
        logger.info("Operational limits updated for platform {}. Reason: {}", 
                   platform.id, payload.getReason());
    }
}
