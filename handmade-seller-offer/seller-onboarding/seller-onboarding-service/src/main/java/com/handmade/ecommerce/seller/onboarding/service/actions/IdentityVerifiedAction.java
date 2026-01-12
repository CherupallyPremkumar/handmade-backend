package com.handmade.ecommerce.seller.onboarding.service.actions;

import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;

import com.handmade.ecommerce.seller.onboarding.dto.IdentityVerifiedPayload;
import com.handmade.ecommerce.seller.onboarding.entity.SellerOnboardingCase;
import com.handmade.ecommerce.seller.onboarding.service.commands.OnboardingResumeContext;
import org.chenile.owiz.OrchExecutor;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Date;

public class IdentityVerifiedAction extends AbstractSTMTransitionAction<SellerOnboardingCase, IdentityVerifiedPayload> {

    @Autowired
    @Qualifier("onboardingResumeExecutor")
    private OrchExecutor<OnboardingResumeContext> onboardingResumeExecutor;

    @Override
    public void transitionTo(SellerOnboardingCase stateEntity, IdentityVerifiedPayload payload,
            State startState, String eventId, State endState,
            STMInternalTransitionInvoker<?> stm, Transition transition) {

        // 1. Update internal state
        if (stateEntity.getSteps() != null) {
            stateEntity.getSteps().stream()
                    .filter(s -> "IDENTITY".equals(s.getStepName()))
                    .reduce((first, second) -> second)
                    .ifPresent(step -> {
                        step.setStatus("VERIFIED");
                        step.setLastUpdated(new Date());
                    });
        }

        // 2. Invoke Resume Execution Flow
        OnboardingResumeContext context = new OnboardingResumeContext(stateEntity.getId(), null);
        try {
            onboardingResumeExecutor.execute(context);
        } catch (Exception e) {
            throw new RuntimeException("Orchestration failed for identity verification resumption", e);
        }
    }
}
