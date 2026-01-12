package com.handmade.ecommerce.seller.onboarding.service.actions;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import com.handmade.ecommerce.seller.onboarding.entity.SellerOnboardingCase;
import com.handmade.ecommerce.seller.onboarding.service.commands.OnboardingResumeContext;
import org.chenile.owiz.OrchExecutor;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.handmade.ecommerce.seller.onboarding.dto.SubmitDocsPayload;

/**
 * Action to handle document submission during onboarding.
 * Delegates to the Resume orchestration flow.
 */
public class SubmitDocsAction extends AbstractSTMTransitionAction<SellerOnboardingCase, SubmitDocsPayload> {

    @Autowired
    @Qualifier("onboardingResumeExecutor")
    private OrchExecutor<OnboardingResumeContext> onboardingResumeExecutor;

    @Override
    public void transitionTo(SellerOnboardingCase stateEntity, SubmitDocsPayload payload,
            State startState, String eventId, State endState,
            STMInternalTransitionInvoker<?> stm, Transition transition) {

        // For submit-docs, we don't necessarily have a Stripe payload,
        // but we can pass document info via the context if needed.
        // For now, we'll trigger the resume orchestration flow.
        OnboardingResumeContext context = new OnboardingResumeContext(stateEntity.getId(), null);
        try {
            onboardingResumeExecutor.execute(context);
        } catch (Exception e) {
            throw new RuntimeException("Orchestration failed for document submission", e);
        }
    }
}
