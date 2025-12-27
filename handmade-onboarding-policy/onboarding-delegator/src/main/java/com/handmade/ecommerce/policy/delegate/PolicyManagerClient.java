package com.handmade.ecommerce.policy.delegate;

import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.seller.domain.enums.SellerType;

import java.util.List;

/**
 * Business Delegate for Policy Management
 * Allows remote calls to the Policy Service
 * Following the same delegation pattern as Platform and Seller Management
 */
public interface PolicyManagerClient {
    
    /**
     * Resolve active onboarding policy for a seller
     * Used by orchestration to determine onboarding requirements
     * 
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param sellerType Seller type
     * @return Active onboarding policy
     */
    OnboardingPolicy resolveOnboardingPolicy(String countryCode, SellerType sellerType);
    
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
}
