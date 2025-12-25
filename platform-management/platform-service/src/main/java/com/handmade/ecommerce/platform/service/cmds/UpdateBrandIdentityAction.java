package com.handmade.ecommerce.platform.service.cmds;

import com.handmade.ecommerce.platform.application.command.UpdateBrandIdentityCommand;
import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Update Brand Identity Action
 * Self-transition in ACTIVE state to update branding
 */
public class UpdateBrandIdentityAction extends AbstractSTMTransitionAction<PlatformOwner, UpdateBrandIdentityCommand> {
    
    private static final Logger logger = LoggerFactory.getLogger(UpdateBrandIdentityAction.class);
    
    @Override
    public void transitionTo(PlatformOwner platform,
                             UpdateBrandIdentityCommand payload,
                             State startState,
                             String eventId,
                             State endState,
                             STMInternalTransitionInvoker<?> stm,
                             Transition transition) throws Exception {
        
        logger.info("Updating brand identity for platform: {}", platform.getId());
        
        // Update brand identity
        if (payload.getBrandName() != null) {
            platform.getBrandIdentity().setBrandName(payload.getBrandName());
        }
        if (payload.getLogoUrl() != null) {
            platform.getBrandIdentity().setLogoUrl(payload.getLogoUrl());
        }
        if (payload.getTagline() != null) {
            platform.getBrandIdentity().setTagline(payload.getTagline());
        }
        
        logger.info("Brand identity updated for platform {}", platform.getId());
    }
}
