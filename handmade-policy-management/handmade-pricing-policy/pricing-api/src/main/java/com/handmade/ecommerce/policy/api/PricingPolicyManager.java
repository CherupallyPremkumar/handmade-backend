package com.handmade.ecommerce.policy.api;

import com.handmade.ecommerce.policy.domain.aggregate.PricingPolicy;
import com.handmade.ecommerce.policy.domain.entity.PricingPolicyRule;
import com.handmade.ecommerce.policy.domain.valueobject.PricingDecision;
import org.chenile.workflow.api.StateEntityService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Enterprise manager for pricing policies
 */
public interface PricingPolicyManager extends StateEntityService<PricingPolicy> {
    
    /**
     * Resolve active policy for country and category
     */
    PricingPolicy resolveActivePolicy(String country, String category, LocalDate date);
    
    /**
     * Get policy by version
     */
    PricingPolicy getPolicyByVersion(String version);
    
    /**
     * Check if pricing is available for country/category
     */
    boolean canPriceInCountry(String country, String category);
    
    /**
     * Get all active policies
     */
    List<PricingPolicy> getAllActivePolicies();
    
    /**
     * Get all draft policies
     */
    List<PricingPolicy> getAllDraftPolicies();

    /**
     * Get rule for a specific pricing check
     */
    Optional<PricingPolicyRule> getRuleForCheck(String policyId, String ruleName);
    
    /**
     * Validate price against active policy
     */
    PricingDecision validatePrice(String country, String category, Long priceCents, 
                                   String productId, String sellerId);
    
    /**
     * Check price change validity
     */
    PricingDecision checkPriceChange(String country, String category, 
                                      Long oldPriceCents, Long newPriceCents,
                                      String productId, String sellerId);
}
