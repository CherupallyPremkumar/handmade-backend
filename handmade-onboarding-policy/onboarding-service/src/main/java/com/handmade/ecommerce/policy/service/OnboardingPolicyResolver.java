package com.handmade.ecommerce.policy.service;

import com.handmade.ecommerce.seller.domain.enums.SellerType;
import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.exception.PolicyNotFoundException;
import com.handmade.ecommerce.policy.repository.OnboardingPolicyRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Resolves the active onboarding policy for a seller
 * 
 * Week 1: Core policy resolution logic
 */
@Service
public class OnboardingPolicyResolver {
    
    private static final Logger logger = LoggerFactory.getLogger(OnboardingPolicyResolver.class);
    
    @Autowired
    private OnboardingPolicyRepository policyRepository;
    
    /**
     * Resolve the active onboarding policy for a seller
     * 
     * @param countryCode ISO 3166-1 alpha-2 country code
     * @param sellerType Seller type
     * @return Active policy
     * @throws NoActivePolicyException if no active policy exists
     */
    public OnboardingPolicy resolveActivePolicy(String countryCode, SellerType sellerType) {
        
        LocalDate today = LocalDate.now();
        
        logger.info("Resolving active policy for country={}, sellerType={}", countryCode, sellerType);
        
        Optional<OnboardingPolicy> policy = policyRepository.findActivePolicy(
            countryCode,
            sellerType,
            today
        );
        
        if (policy.isEmpty()) {
            String message = String.format(
                "No active onboarding policy for country=%s, sellerType=%s",
                countryCode, sellerType
            );
            logger.error(message);
            throw new NoActivePolicyException(message);
        }
        
        OnboardingPolicy activePolicy = policy.get();
        logger.info("Resolved policy: version={}, id={}", 
                   activePolicy.getVersion(), activePolicy.getId());
        
        return activePolicy;
    }
    
    /**
     * Check if an active policy exists
     */
    public boolean hasActivePolicy(String countryCode, SellerType sellerType) {
        return policyRepository.hasActivePolicy(countryCode, sellerType, LocalDate.now());
    }
    /**
     * Get policy by version
     */
    public OnboardingPolicy getPolicyByVersion(String version) {
        return policyRepository.findByVersion(version)
            .orElseThrow(() -> new PolicyNotFoundException("Policy version not found: " + version));
    }

    /**
     * Check if onboarding is available for country
     */
    public boolean canOnboardInCountry(String country, SellerType sellerType) {
        return hasActivePolicy(country, sellerType);
    }

    /**
     * Get all active policies
     */
    public java.util.List<OnboardingPolicy> getAllActivePolicies() {
        return policyRepository.findAllActivePolicies(java.time.LocalDate.now());
    }

    /**
     * Get all draft policies
     */
    public java.util.List<OnboardingPolicy> getAllDraftPolicies() {
        return policyRepository.findAllDraftPolicies();
    }
}
