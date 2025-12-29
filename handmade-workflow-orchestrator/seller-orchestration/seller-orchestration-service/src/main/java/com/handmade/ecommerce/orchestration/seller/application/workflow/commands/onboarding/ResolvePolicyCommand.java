package com.handmade.ecommerce.orchestration.seller.application.workflow.commands.onboarding;

import com.handmade.ecommerce.orchestration.seller.context.SellerOnboardingContext;
import com.handmade.ecommerce.orchestration.seller.application.service.exception.OrchestrationException;
import com.handmade.ecommerce.platform.core.time.PlatformClock;
import com.handmade.ecommerce.policy.ResolvedOnboardingPolicyView;
import com.handmade.ecommerce.policy.delegate.OnboardingPolicyManagerClient;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import org.chenile.owiz.Command;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Command: Resolve the applicable onboarding policy based on country and seller
 * type.
 */
@Component("resolve-policy-command")
public class ResolvePolicyCommand implements Command<SellerOnboardingContext> {

    private final OnboardingPolicyManagerClient policyManagerClient;
    private final PlatformClock platformClock;

    public ResolvePolicyCommand(
            OnboardingPolicyManagerClient policyManagerClient,
            PlatformClock platformClock) {
        this.policyManagerClient = policyManagerClient;
        this.platformClock = platformClock;
    }

    @Override
    public void execute(SellerOnboardingContext context) {

        // Idempotency guard
        if (context.isPolicyResolved()) {
            return;
        }

        // Validate and convert seller type (Internal to this command/layer)
        SellerType sellerType;
        try {
            sellerType = SellerType.valueOf(context.getSellerType());
        } catch (Exception e) {
            throw new OrchestrationException(
                    "Invalid sellerType provided in context: " + context.getSellerType(), e);
        }

        // Use platform clock for date resolution
        LocalDate effectiveDate = platformClock.today();

        ResolvedOnboardingPolicyView policy = policyManagerClient.resolveOnboardingPolicy(
                context.getCountry(),
                sellerType,
                effectiveDate);

        if (policy == null) {
            throw new OrchestrationException(
                    "No onboarding policy found for country="
                            + context.getCountry() + ", sellerType=" + sellerType);
        }

        // Store policy metadata in context
        context.setPolicyId(policy.getPolicyId());
        context.setPolicyVersion(policy.getVersion());

        // Store verification requirements in context
        context.setIdentityVerificationRequired(policy.isIdentityVerificationRequired());
        context.setTaxVerificationRequired(policy.isTaxVerificationRequired());
        context.setBankVerificationRequired(policy.isBankVerificationRequired());
        context.setManualApprovalRequired(policy.isManualApprovalRequired());
    }
}
