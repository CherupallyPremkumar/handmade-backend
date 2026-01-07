package com.handmade.ecommerce.policy.api;

import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.domain.entity.OnboardingPolicyRule;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import org.chenile.workflow.api.StateEntityService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Enterprise manager for onboarding policies.
 * Consolidates CRUD, state management, and resolution logic.
 */
public interface OnboardingPolicyManager extends StateEntityService<OnboardingPolicy> {
    
    /**
     * Resolve active policy for country and seller type
     */
    OnboardingPolicy resolveActivePolicy(String country, SellerType sellerType, LocalDate date);
    
    /**
     * Get policy by version
     */
    OnboardingPolicy getPolicyByVersion(String version);
    
    /**
     * Check if onboarding is available for country
     */
    boolean canOnboardInCountry(String country, SellerType sellerType);
    
    /**
     * Get all active policies
     */
    List<OnboardingPolicy> getAllActivePolicies();
    
    /**
     * Get all draft policies
     */
    List<OnboardingPolicy> getAllDraftPolicies();

    /**
     * Get rule for a specific onboarding step from the policy
     */
    Optional<OnboardingPolicyRule> getRuleForStep(String policyId, String stepName);
}
