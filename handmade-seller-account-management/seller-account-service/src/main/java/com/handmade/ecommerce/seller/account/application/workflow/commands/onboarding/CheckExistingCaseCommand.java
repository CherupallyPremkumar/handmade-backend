package com.handmade.ecommerce.seller.account.application.workflow.commands.onboarding;

import com.handmade.ecommerce.seller.account.delegate.OnboardingManagerClient;
import com.handmade.ecommerce.seller.account.dto.ActiveSellerAccountView;
import com.handmade.ecommerce.seller.account.context.SellerOnboardingContext;
import com.handmade.ecommerce.seller.account.application.service.exception.OrchestrationException;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Command: Check if an active onboarding case already exists for the
 * email/country.
 */
@Component("check-existing-case-command")
public class CheckExistingCaseCommand implements Command<SellerOnboardingContext> {

    private static final Logger logger = LoggerFactory.getLogger(CheckExistingCaseCommand.class);

    @Autowired
    private OnboardingManagerClient onboardingManagerClient;

    @Override
    public void execute(SellerOnboardingContext context) {
        try {
            Optional<ActiveSellerAccountView> activeCase = onboardingManagerClient.findActiveCase(
                    context.getEmail(),
                    context.getCountry());

            if (activeCase.isPresent()) {
                ActiveSellerAccountView view = activeCase.get();

                logger.info("Active onboarding case already exists. email={}, country={}, caseId={}",
                        context.getEmail(), context.getCountry(), view.getCaseId());

                context.setCaseExists(true);
                context.setCaseId(view.getCaseId());
            } else {
                context.setCaseExists(false);
            }

        } catch (Exception ex) {
            logger.error("Failed to check existing onboarding case. email={}, country={}",
                    context.getEmail(), context.getCountry(), ex);
            throw new OrchestrationException("Unable to verify existing onboarding case", ex);
        }
    }
}
