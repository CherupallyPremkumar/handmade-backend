package com.handmade.ecommerce.policy.delegate;

import com.handmade.ecommerce.policy.ResolvedOnboardingPolicyView;
import com.handmade.ecommerce.policy.api.OnboardingPolicyManager;
import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.domain.entity.OnboardingPolicyRule;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of Policy Manager Client
 * Delegates calls to remote Policy Service via Chenile Proxy
 */
public class OnboardingPolicyManagerClientImpl implements OnboardingPolicyManagerClient {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    @Qualifier("policyServiceProxy")
    private OnboardingPolicyManager policyManager;


    @Override
    public ResolvedOnboardingPolicyView resolveOnboardingPolicy(String countryCode, SellerType sellerType, LocalDate date) {
        logger.info("Resolving onboarding policy via delegate: country={}, sellerType={}",
                countryCode, sellerType);
        OnboardingPolicy policy = policyManager.resolveActivePolicy(countryCode, sellerType, date);
        if (policy == null) return null;

        return ResolvedOnboardingPolicyView.builder()
                .policyId(policy.getId())
                .version(policy.getPolicyVersion())
                .country(policy.getCountryCode())
                .sellerType(policy.getSellerType())
                .effectiveDate(policy.getEffectiveDate())
                .identityVerificationRequired(policy.isIdentityVerificationRequired())
                .taxVerificationRequired(policy.isTaxVerificationRequired())
                .bankVerificationRequired(policy.isBankVerificationRequired())
                .manualApprovalRequired(policy.isManualApprovalRequired())
                .build();
    }

    @Override
    public OnboardingPolicy getPolicy(String id) {
        logger.debug("Retrieving policy: {}", id);
        return policyManager.retrieve(id).getMutatedEntity();
    }

    @Override
    public OnboardingPolicy getPolicyByVersion(String version) {
        logger.debug("Retrieving policy by version: {}", version);
        return policyManager.getPolicyByVersion(version);
    }

    @Override
    public List<OnboardingPolicy> getAllActivePolicies() {
        logger.debug("Retrieving all active policies");
        return policyManager.getAllActivePolicies();
    }

    @Override
    public boolean canOnboardInCountry(String countryCode, SellerType sellerType) {
        logger.debug("Checking if onboarding available: country={}, sellerType={}", 
                    countryCode, sellerType);
        return policyManager.canOnboardInCountry(countryCode, sellerType);
    }

    @Override
    public Optional<OnboardingPolicyRule> getRuleForStep(String policyId, String stepName) {
        logger.debug("Retrieving rule for step: {} for policy: {}", stepName, policyId);
        return policyManager.getRuleForStep(policyId, stepName);
    }
}
