package com.handmade.ecommerce.onboarding.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.onboarding.domain.aggregate.SellerOnboardingCase;
import com.handmade.ecommerce.onboarding.dto.SubmitApplicationPayload;

import java.time.LocalDateTime;

public class SubmitApplicationAction extends AbstractSTMTransitionAction<SellerOnboardingCase, SubmitApplicationPayload> {

    @Override
    public void transitionTo(SellerOnboardingCase seller, SubmitApplicationPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Validate that seller has minimum required information
        validateSellerApplication(seller);
        
        // Set submission timestamp
        seller.setUpdatedAt(LocalDateTime.now());
    }
    
    private void validateSellerApplication(SellerOnboardingCase seller) {
        if (seller.getBusinessName() == null || seller.getBusinessName().isEmpty()) {
            throw new IllegalStateException("Business name is required");
        }
        if (seller.getEmail() == null || seller.getEmail().isEmpty()) {
            throw new IllegalStateException("Email is required");
        }
    }
}
