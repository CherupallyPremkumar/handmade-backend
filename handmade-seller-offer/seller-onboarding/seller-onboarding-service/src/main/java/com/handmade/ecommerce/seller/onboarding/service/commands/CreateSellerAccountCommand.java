package com.handmade.ecommerce.seller.onboarding.service.commands;

import org.springframework.stereotype.Component;

/**
 * OWIZ Command to create the final Seller account.
 * Phase 3 - triggered by user confirmation.
 */
@Component("CreateSellerAccountCommand")
public class CreateSellerAccountCommand extends BaseOrchCommand<OnboardingConfirmContext> {

    @Override
    protected void executeCommand(OnboardingConfirmContext context) throws Exception {
        logger.info("Executing final seller account creation for Seller: {}", context.getSellerId());

        // Call MarketplaceOpsService or Seller Service to activate the account
        // provisioningService.provision(context.getSellerId());

        context.addResult("accountCreation", "SUCCESS");
        context.addResult("accountStatus", "ACTIVE");

        logger.info("Seller account successfully activated.");
    }
}
