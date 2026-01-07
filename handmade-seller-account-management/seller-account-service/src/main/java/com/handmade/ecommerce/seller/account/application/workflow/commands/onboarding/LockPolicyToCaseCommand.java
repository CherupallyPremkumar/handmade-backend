package com.handmade.ecommerce.seller.account.application.workflow.commands.onboarding;

import com.handmade.ecommerce.seller.account.delegate.OnboardingManagerClient;
import com.handmade.ecommerce.seller.account.dto.LockPolicyRequest;
import com.handmade.ecommerce.seller.account.context.SellerOnboardingContext;
import com.handmade.ecommerce.seller.account.application.service.exception.OrchestrationException;
import org.chenile.owiz.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Command: Lock the resolved policy version to the specific onboarding case.
 */
@Component("lock-policy-to-case-command")
public class LockPolicyToCaseCommand implements Command<SellerOnboardingContext> {

    @Autowired
    private OnboardingManagerClient onboardingManagerClient;

    @Override
    public void execute(SellerOnboardingContext context) {
        if (context.getCaseId() == null || !context.isPolicyResolved()) {
            throw new OrchestrationException("Preconditions failed: caseId and policy details must be present");
        }

        try {
            LockPolicyRequest request = new LockPolicyRequest();
            request.setPolicyId(context.getPolicyId());
            request.setPolicyVersion(context.getPolicyVersion());

            onboardingManagerClient.lockPolicy(context.getCaseId(), request);
        } catch (Exception e) {
            throw new OrchestrationException("Failed to lock policy to case: " + context.getCaseId(), e);
        }
    }
}
