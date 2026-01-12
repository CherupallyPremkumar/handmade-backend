package com.handmade.ecommerce.seller.onboarding.service.actions;

import com.handmade.ecommerce.seller.onboarding.dto.StartOnboardingPayload;
import com.handmade.ecommerce.seller.onboarding.entity.SellerOnboardingCase;
import com.handmade.ecommerce.seller.onboarding.service.commands.OnboardingInitContext;
import org.chenile.owiz.OrchExecutor;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class StartOnboardingAction extends AbstractSTMTransitionAction<SellerOnboardingCase, StartOnboardingPayload> {

    @Autowired
    @Qualifier("onboardingInitExecutor")
    private OrchExecutor<OnboardingInitContext> onboardingInitExecutor;

    @Override
    public void transitionTo(SellerOnboardingCase stateEntity, StartOnboardingPayload payload,
            State startState, String eventId, State endState,
            STMInternalTransitionInvoker<?> stm, Transition transition) {

        OnboardingInitContext context = new OnboardingInitContext(stateEntity.getSellerId(), stateEntity.getId());
        try {
            onboardingInitExecutor.execute(context);
        } catch (Exception e) {
            throw new RuntimeException("Orchestration failed for onboarding initiation", e);
        }
    }
}
