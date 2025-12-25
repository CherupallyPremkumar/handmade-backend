package com.handmade.ecommerce.platform.service.cmds;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.dto.UpdateFeatureConfigPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action to update platform feature configuration
 */
public class UpdateFeatureConfigAction extends AbstractSTMTransitionAction<PlatformOwner, UpdateFeatureConfigPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(UpdateFeatureConfigAction.class);
    
    @Override
    public void transitionTo(PlatformOwner platform,
                            UpdateFeatureConfigPayload payload,
                            State startState,
                            String eventId,
                            State endState,
                            STMInternalTransitionInvoker<?> stm,
                            Transition transition) throws Exception {
        
        logger.info("Updating feature configuration for platform: {}", platform.id);
        
        // TODO: Update platform.activeFeatureConfigId with new configuration
        if (payload.getIsSellerRegistrationOpen() != null) {
            logger.info("Seller Registration: {}", payload.getIsSellerRegistrationOpen());
        }
        if (payload.getIsCheckoutEnabled() != null) {
            logger.info("Checkout Enabled: {}", payload.getIsCheckoutEnabled());
        }
        if (payload.getIsGuestCheckoutAllowed() != null) {
            logger.info("Guest Checkout: {}", payload.getIsGuestCheckoutAllowed());
        }
        
        logger.info("Feature configuration updated for platform {}", platform.id);
    }
}
