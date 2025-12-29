package com.handmade.ecommerce.onboarding.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.onboarding.domain.aggregate.SellerOnboardingCase;
import com.handmade.ecommerce.onboarding.dto.ApproveSellerPayload;

import java.time.LocalDateTime;

public class ApproveSellerAction extends AbstractSTMTransitionAction<SellerOnboardingCase, ApproveSellerPayload> {

    @Override
    public void transitionTo(SellerOnboardingCase seller, ApproveSellerPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Set approval timestamp
        seller.setUpdatedAt(LocalDateTime.now());
    }
}
