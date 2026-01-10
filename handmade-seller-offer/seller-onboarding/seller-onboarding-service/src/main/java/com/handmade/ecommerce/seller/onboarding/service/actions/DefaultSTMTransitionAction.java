package com.handmade.ecommerce.seller.onboarding.service.actions;

import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.param.MinimalPayload;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.onboarding.entity.SellerOnboardingCase;


public class DefaultSTMTransitionAction<PayloadType extends MinimalPayload>
    extends AbstractSTMTransitionAction<SellerOnboardingCase, PayloadType> {
    @Override
    public void transitionTo(SellerOnboardingCase sellerOnboardingCase, PayloadType payload,
                 State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm,
                 Transition transition) {

    }
}