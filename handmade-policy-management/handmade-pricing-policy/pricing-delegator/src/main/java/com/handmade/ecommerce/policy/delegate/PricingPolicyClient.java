package com.handmade.ecommerce.policy.delegate;

import com.handmade.ecommerce.policy.domain.aggregate.PricingPolicy;
import com.handmade.ecommerce.policy.domain.valueobject.PricingDecision;

/**
 * Client interface for other modules to call pricing policy
 */
public interface PricingPolicyClient {
    
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
    
    /**
     * Get active policy for country/category
     */
    PricingPolicy getActivePolicy(String country, String category);
}
