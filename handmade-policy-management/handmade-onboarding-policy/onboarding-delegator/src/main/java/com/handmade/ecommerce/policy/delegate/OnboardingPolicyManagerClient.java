package com.handmade.ecommerce.policy.delegate;

import com.handmade.ecommerce.policy.ResolvedOnboardingPolicyView;
import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.domain.entity.OnboardingPolicyRule;
import com.handmade.ecommerce.platform.domain.enums.SellerType;

import java.time.LocalDate;
import java.util.Optional;

import java.util.List;

/**
 * Business Delegate for Policy Management
 * Allows remote calls to the Policy Service
 * Following the same delegation pattern as Platform and Seller Management
 */
public interface OnboardingPolicyManagerClient {
    
    /**
     * Resolve active onboarding policy for a seller
     * Used by orchestration to determine onboarding requirements
     * 
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param sellerType Seller type
     * @return Active onboarding policy
     */
    ResolvedOnboardingPolicyView resolveOnboardingPolicy(String countryCode, SellerType sellerType, LocalDate date);
    
    /**
     * Get policy by ID
     */
    OnboardingPolicy getPolicy(String id);
    
    /**
     * Get policy by version
     */
    OnboardingPolicy getPolicyByVersion(String version);
    
    /**
     * Get all active policies
     */
    List<OnboardingPolicy> getAllActivePolicies();
    
    /**
     * Check if onboarding is available for country and seller type
     */
    boolean canOnboardInCountry(String countryCode, SellerType sellerType);

    /**
     * Get rule for a specific onboarding step from the policy
     */
    Optional<OnboardingPolicyRule> getRuleForStep(String policyId, String stepName);
}
