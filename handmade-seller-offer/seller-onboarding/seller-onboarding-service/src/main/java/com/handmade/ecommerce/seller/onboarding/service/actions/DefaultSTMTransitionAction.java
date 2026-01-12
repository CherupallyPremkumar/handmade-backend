package com.handmade.ecommerce.seller.onboarding.service.actions;

import com.handmade.ecommerce.seller.onboarding.entity.SellerOnboardingCase;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.StateEntity;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.param.MinimalPayload;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

public class DefaultSTMTransitionAction<PayloadType extends MinimalPayload>
        extends AbstractSTMTransitionAction<SellerOnboardingCase, PayloadType> {
    @Override
    public void transitionTo(SellerOnboardingCase platformOwner, PayloadType payload,
                             State startState, String eventId, State endState, STMInternalTransitionInvoker<?> stm,
                             Transition transition) {

    }
}