package com.handmade.ecommerce.seller.account.application.workflow.commands.onboarding;

import com.handmade.ecommerce.seller.account.delegate.OnboardingManagerClient;
import com.handmade.ecommerce.seller.account.dto.CreateSellerAccountRequest;
import com.handmade.ecommerce.seller.account.context.SellerOnboardingContext;
import com.handmade.ecommerce.seller.account.application.service.exception.OrchestrationException;

import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OWIZ Command: Create a new onboarding case in DRAFT state.
 */
@Component("create-case-command")
public class CreateCaseCommand implements Command<SellerOnboardingContext> {

    private static final Logger logger = LoggerFactory.getLogger(CreateCaseCommand.class);

    @Autowired
    private OnboardingManagerClient onboardingManagerClient;

    @Override
    public void execute(SellerOnboardingContext context) {

        // Safety guard (avoid duplicate creation if caseId is already set)
        if (context.getCaseId() != null) {
            return;
        }

        // Mandatory precondition
        if (!context.isPolicyResolved()) {
            throw new OrchestrationException("Policy must be resolved before creating onboarding case");
        }

        try {
            CreateSellerAccountRequest request = new CreateSellerAccountRequest();
            request.setEmail(context.getEmail());
            request.setCountry(context.getCountry());
            request.setSellerType(context.getSellerType());
            request.setPolicyId(context.getPolicyId());
            request.setPolicyVersion(context.getPolicyVersion());
            request.setIdentityVerificationRequired(context.isIdentityVerificationRequired());
            request.setTaxVerificationRequired(context.isTaxVerificationRequired());
            request.setBankVerificationRequired(context.isBankVerificationRequired());
            request.setManualApprovalRequired(context.isManualApprovalRequired());

            String caseId = onboardingManagerClient.createCase(request);
            context.setCaseId(caseId);

            logger.info("Successfully created onboarding case. caseId={}, email={}", caseId, context.getEmail());
        } catch (Exception e) {
            throw new OrchestrationException("Failed to create SellerAccount", e);
        }
    }
}
