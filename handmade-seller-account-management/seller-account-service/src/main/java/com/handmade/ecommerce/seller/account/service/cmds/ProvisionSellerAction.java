package com.handmade.ecommerce.seller.account.service.cmds;

import com.handmade.ecommerce.event.api.EventPublisher;
import com.handmade.ecommerce.identity.event.OnboardingCompletedEvent;
import com.handmade.ecommerce.seller.account.domain.aggregate.SellerAccount;
import org.chenile.stm.STMInternalTransitionInvoker;
import org.chenile.stm.State;
import org.chenile.stm.model.Transition;
import org.chenile.workflow.service.stmcmds.AbstractSTMTransitionAction;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

/**
 * ProvisionSellerAction - Triggered when onboarding is provisioned to ACTIVE
 * state.
 * Publishes OnboardingCompletedEvent to start the orchestrated activation flow.
 */
public class ProvisionSellerAction extends AbstractSTMTransitionAction<SellerAccount, Object> {

    @Autowired
    private EventPublisher eventPublisher;

    @Override
    public void transitionTo(SellerAccount onboardingCase, Object transitionParam, State startState,
            String eventId,
            State endState, STMInternalTransitionInvoker<?> stm, Transition transition) throws Exception {

        onboardingCase.setUpdatedAt(LocalDateTime.now());

        // Publish event to trigger orchestrator
        OnboardingCompletedEvent event = new OnboardingCompletedEvent(
                onboardingCase.getId(),
                onboardingCase.getId(), // Using Case ID as initial Seller ID placeholder
                onboardingCase.getEmail(),
                onboardingCase.getCountry());
        eventPublisher.publish("ONBOARDING_COMPLETED", event);
    }
}
