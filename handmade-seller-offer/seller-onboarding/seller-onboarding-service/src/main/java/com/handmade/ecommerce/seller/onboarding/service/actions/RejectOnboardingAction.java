package com.handmade.ecommerce.seller.onboarding.service.actions;

import com.handmade.ecommerce.seller.onboarding.api.dto.RejectOnboardingPayload;
import com.handmade.ecommerce.seller.onboarding.entity.SellerOnboardingCase;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import java.util.Date;

public class RejectOnboardingAction extends DefaultSTMTransitionAction<RejectOnboardingPayload> {

    @Override
    public void transitionTo(SellerOnboardingCase stateEntity, RejectOnboardingPayload payload,
                            State startState, String eventId, State endState, 
                            STMInternalTransitionInvoker<?> stm, Transition transition) {
        stateEntity.setRejectedAt(new Date());
    }
}
