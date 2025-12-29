package com.handmade.ecommerce.policy.service.cmds;

import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.dto.UpdateOnboardingPolicyPayload;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Update Onboarding Policy Action
 * Can only be performed in DRAFT state.
 */
public class UpdateOnboardingPolicyAction extends AbstractSTMTransitionAction<OnboardingPolicy, UpdateOnboardingPolicyPayload> {
    
    private static final Logger logger = LoggerFactory.getLogger(UpdateOnboardingPolicyAction.class);
    
    @Override
    public void transitionTo(OnboardingPolicy policy,
                             UpdateOnboardingPolicyPayload payload,
                             State startState,
                             String eventId,
                             State endState,
                             STMInternalTransitionInvoker<?> stm,
                             Transition transition) throws Exception {
        
        logger.info("Updating onboarding policy: {}", policy.getId());
        
        if (payload.policyVersion != null) policy.setPolicyVersion(payload.policyVersion);
        if (payload.countryCode != null) policy.setCountryCode(payload.countryCode);
        if (payload.sellerType != null) policy.setSellerType(payload.sellerType);
        if (payload.effectiveDate != null) policy.setEffectiveDate(payload.effectiveDate);
        if (payload.description != null) policy.setDescription(payload.description);
        if (payload.regulatoryBasis != null) policy.setRegulatoryBasis(payload.regulatoryBasis);
        
        logger.info("Onboarding policy {} updated successfully", policy.getId());
    }
}
