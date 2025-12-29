package com.handmade.ecommerce.orchestration.seller.application.workflow.commands.onboarding;

import com.handmade.ecommerce.orchestration.seller.context.SellerOnboardingContext;
import com.handmade.ecommerce.orchestration.seller.dto.StartOnboardingResponse;
import org.chenile.owiz.Command;
import org.springframework.stereotype.Component;

/**
 * Command: Return the details of an already existing active onboarding case.
 */
@Component("return-existing-case-command")
public class ReturnExistingCaseCommand implements Command<SellerOnboardingContext> {

    @Override
    public void execute(SellerOnboardingContext context) {
        if (!context.isCaseExists()) {
            return;
        }

        StartOnboardingResponse response = context.getResponse();
        response.setCaseId(context.getCaseId());
        response.setStatus("EXISTING_ACTIVE_CASE");
        response.setMessage("An active onboarding case already exists for this account.");

        // In a real scenario, we might also want to return the current stage/status of
        // that case
    }
}
