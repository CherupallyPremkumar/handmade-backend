package com.handmade.ecommerce.onboarding.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.onboarding.domain.aggregate.SellerOnboardingCase;
import com.handmade.ecommerce.onboarding.dto.RequestMoreInfoPayload;

import java.time.LocalDateTime;

public class RequestMoreInfoAction extends AbstractSTMTransitionAction<SellerOnboardingCase, RequestMoreInfoPayload> {

    @Override
    public void transitionTo(SellerOnboardingCase seller, RequestMoreInfoPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Store the information request details
        if (payload.getInfoRequested() != null) {
            seller.setUpdatedAt(LocalDateTime.now());
        }
    }
}
