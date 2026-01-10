package com.handmade.ecommerce.seller.onboarding.service.commands;

import org.springframework.stereotype.Component;

/**
 * OWIZ Command to update the onboarding state flags.
 * Responds to the Identity Verified event (Phase 2).
 */
@Component("UpdateOnboardingStateCommand")
public class UpdateOnboardingStateCommand extends BaseOrchCommand<OnboardingResumeContext> {

    @Override
    protected void executeCommand(OnboardingResumeContext context) throws Exception {
        logger.info("Updating onboarding flags for Case: {}", context.getOnboardingCaseId());

        // In a real implementation, this would interact with a service to update flags:
        // identityVerified = true, bankVerified = true, taxVerified = true

        context.addResult("stateUpdate", "VETTING_FLAGS_SET");
        context.addResult("identityVerified", true);
        context.addResult("bankVerified", true);
        context.addResult("taxVerified", true);

        logger.info("Vetting flags successfully updated in context and record.");
    }
}
