package com.handmade.ecommerce.onboarding.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.onboarding.domain.aggregate.SellerOnboardingCase;
import com.handmade.ecommerce.onboarding.dto.VerifyBusinessDocumentsPayload;

import java.time.LocalDateTime;

public class VerifyBusinessDocumentsAction extends AbstractSTMTransitionAction<SellerOnboardingCase, VerifyBusinessDocumentsPayload> {

    @Override
    public void transitionTo(SellerOnboardingCase seller, VerifyBusinessDocumentsPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Validate verification result
        if (payload.isVerified()) {
            // Document verification successful
            seller.setUpdatedAt(LocalDateTime.now());
        }
    }
}
