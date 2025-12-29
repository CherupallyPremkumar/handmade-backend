package com.handmade.ecommerce.onboarding.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.onboarding.domain.aggregate.SellerOnboardingCase;
import com.handmade.ecommerce.onboarding.dto.ReapplyPayload;

import java.time.LocalDateTime;

public class ReapplyAction extends AbstractSTMTransitionAction<SellerOnboardingCase, ReapplyPayload> {

    @Override
    public void transitionTo(SellerOnboardingCase seller, ReapplyPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Reset seller for reapplication
        seller.setUpdatedAt(LocalDateTime.now());
    }
}
