package com.handmade.ecommerce.onboarding.service.cmds;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.onboarding.domain.aggregate.SellerOnboardingCase;
import com.handmade.ecommerce.onboarding.dto.CompleteTaxInterviewPayload;

import java.time.LocalDateTime;

public class CompleteTaxInterviewAction extends AbstractSTMTransitionAction<SellerOnboardingCase, CompleteTaxInterviewPayload> {

    @Override
    public void transitionTo(SellerOnboardingCase seller, CompleteTaxInterviewPayload payload, State startState, String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {
        
        // Update seller with tax info
        seller.getBusinessDetails().setTaxId(payload.getTaxId());
        seller.setUpdatedAt(LocalDateTime.now());
    }
}
