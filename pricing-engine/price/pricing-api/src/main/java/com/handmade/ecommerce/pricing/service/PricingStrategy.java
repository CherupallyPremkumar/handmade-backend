package com.handmade.ecommerce.pricing.service;

import com.handmade.ecommerce.core.model.Money;

public interface PricingStrategy {

    /**
     * Get region code this strategy handles
     * 
     * @return Region code (e.g., "IN", "US", "EU")
     */
    String getRegionCode();

    /**
     * Get current price for variant in this region
     * 
     * @param variantId Product variant ID
     * @return Price in regional currency
     */
    Money getCurrentPrice(String variantId);

    /**
     * Calculate tax for this region
     * 
     * @param amount Amount to calculate tax on
     * @return Tax amount
     */
    Money calculateTax(Money amount);
}
