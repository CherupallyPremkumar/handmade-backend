package com.handmade.ecommerce.seller.onboarding.service.commands;

import com.handmade.ecommerce.seller.onboarding.api.dto.StripeConfigResponse;
import com.handmade.ecommerce.seller.onboarding.api.port.StripeIdentityAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OWIZ Command to verify KYC.
 * In Amazon-grade architecture, this command starts the ASYNCHRONOUS session
 * and returns immediately, allowing the orchestration to pause.
 */
@Component("VerifyKycCommand")
public class VerifyKycCommand extends BaseOrchCommand<OnboardingInitContext> {

    @Autowired
    private StripeIdentityAdapter stripeIdentityAdapter;

    @Override
    protected void executeCommand(OnboardingInitContext context) throws Exception {
        logger.info("Initiating Async KYC for seller: {} (Case: {})",
                context.getSellerId(), context.getOnboardingCaseId());

        // 1. Call Stripe Adapter to create verification session
        StripeConfigResponse config = stripeIdentityAdapter.createVerificationSession(
                context.getOnboardingCaseId(),
                context.getSellerId());

        // 2. Add results to context (can be used by UI or following handlers)
        context.addResult("verifyKyc", "SESSION_CREATED");
        context.addResult("stripeConfig", config);

        logger.info("KYC Initiation Complete. Flow paused for external hand-off.");
    }
}
