package com.handmade.ecommerce.seller.onboarding.service.actions;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

import com.handmade.ecommerce.seller.onboarding.dto.ConfirmOnboardingPayload;
import com.handmade.ecommerce.seller.onboarding.entity.SellerOnboardingCase;
import com.handmade.ecommerce.seller.onboarding.service.commands.OnboardingConfirmContext;
import org.chenile.owiz.OrchExecutor;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ConfirmOnboardingAction
        extends AbstractSTMTransitionAction<SellerOnboardingCase, ConfirmOnboardingPayload> {

    @Autowired
    @Qualifier("onboardingConfirmExecutor")
    private OrchExecutor<OnboardingConfirmContext> onboardingConfirmExecutor;

    @Override
    public void transitionTo(SellerOnboardingCase stateEntity, ConfirmOnboardingPayload payload,
            State startState, String eventId, State endState,
            STMInternalTransitionInvoker<?> stm, Transition transition) {

        OnboardingConfirmContext context = new OnboardingConfirmContext(stateEntity.getSellerId(), stateEntity.getId());
        try {
            onboardingConfirmExecutor.execute(context);
        } catch (Exception e) {
            throw new RuntimeException("Orchestration failed for onboarding confirmation", e);
        }
    }
}
