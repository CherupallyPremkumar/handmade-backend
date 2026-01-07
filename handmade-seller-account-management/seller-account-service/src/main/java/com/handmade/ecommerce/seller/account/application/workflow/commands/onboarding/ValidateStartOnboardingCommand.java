package com.handmade.ecommerce.seller.account.application.workflow.commands.onboarding;

import com.handmade.ecommerce.seller.account.context.SellerOnboardingContext;
import org.chenile.base.exception.BadRequestException;
import org.chenile.owiz.Command;
import org.springframework.stereotype.Component;

/**
 * Command: Validate input parameters for starting the onboarding process.
 */
@Component("validate-start-onboarding-command")
public class ValidateStartOnboardingCommand implements Command<SellerOnboardingContext> {

    @Override
    public void execute(SellerOnboardingContext context) {
        if (context.getEmail() == null || context.getEmail().trim().isEmpty()) {
            throw new BadRequestException("Email is mandatory for starting onboarding");
        }
        if (context.getCountry() == null || context.getCountry().trim().isEmpty()) {
            throw new BadRequestException("Country is mandatory for starting onboarding");
        }
        if (context.getSellerType() == null || context.getSellerType().trim().isEmpty()) {
            throw new BadRequestException("Seller type is mandatory for starting onboarding");
        }
    }
}
