package com.handmade.ecommerce.onboarding.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.onboarding.domain.aggregate.SellerOnboardingCase;
import com.handmade.ecommerce.onboarding.dto.RejectSellerPayload;

import java.time.LocalDateTime;

public class RejectSellerAction extends AbstractSTMTransitionAction<SellerOnboardingCase, RejectSellerPayload> {


    @Override
    public void transitionTo(SellerOnboardingCase seller, RejectSellerPayload payload, State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {

        // Store rejection reason
        if (payload.getRejectionReason() != null) {
            seller.setUpdatedAt(LocalDateTime.now());
        }
    }
}
