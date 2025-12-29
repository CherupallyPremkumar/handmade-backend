package com.handmade.ecommerce.onboarding.infrastructure.events;

import com.handmade.ecommerce.identity.domain.event.IdentityVerifiedEvent;
import com.handmade.ecommerce.onboarding.api.OnBoardingInternalCaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listener for Identity Events.
 * This bridges the Identity context with the Onboarding context.
 * Decouples modules using Spring Application Events.
 */
@Component
public class IdentityEventListener {

    private static final Logger logger = LoggerFactory.getLogger(IdentityEventListener.class);
    private final OnBoardingInternalCaseService onboardingInternalCaseService;

    public IdentityEventListener(OnBoardingInternalCaseService onboardingInternalCaseService) {
        this.onboardingInternalCaseService = onboardingInternalCaseService;
    }

    /**
     * Handle IdentityVerifiedEvent by updating the onboarding case.
     */
    @EventListener
    public void onIdentityVerified(IdentityVerifiedEvent event) {
        logger.info("Received IdentityVerifiedEvent for case: {}", event.getOnboardingCaseId());

        try {
            onboardingInternalCaseService.identityVerified(event.getOnboardingCaseId(), event.getResult());
        } catch (Exception e) {
            logger.error("Error updating onboarding case {} after identity verification",
                    event.getOnboardingCaseId(), e);
            // In a production environment, we might want to retry this or put it in a DLQ
            throw e;
        }
    }
}
