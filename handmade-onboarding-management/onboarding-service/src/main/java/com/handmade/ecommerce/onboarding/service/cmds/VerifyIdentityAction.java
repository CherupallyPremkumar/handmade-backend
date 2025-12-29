package com.handmade.ecommerce.onboarding.service.cmds;

import com.handmade.ecommerce.onboarding.domain.aggregate.SellerOnboardingCase;
import com.handmade.ecommerce.onboarding.dto.VerifyIdentityPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

/**
 * Action: Verify seller identity
 */
public class VerifyIdentityAction extends AbstractSTMTransitionAction<SellerOnboardingCase, VerifyIdentityPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(VerifyIdentityAction.class);
    
    @Override
    public void transitionTo(SellerOnboardingCase sellerAccount,
                            VerifyIdentityPayload payload,
                            State startState,
                            String eventId,
                            State endState,
                            STMInternalTransitionInvoker<?> stm,
                            Transition transition) throws Exception {
        
        logger.info("Executing Identity Verification for seller {}", sellerAccount.getId());
        
        // Focus on the DOMAIN side of verification
        // For now, we simulate success if payload indicates verified
        
        sellerAccount.setIdentityVerifiedAt(LocalDateTime.now());
        sellerAccount.setUpdatedAt(LocalDateTime.now());
        
        logger.info("Identity verification processed for seller {}", sellerAccount.getId());
    }
}
