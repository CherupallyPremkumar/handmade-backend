package com.handmade.ecommerce.pricing;

import com.handmade.ecommerce.command.CreatePriceCommand;
import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.pricing.model.Price;
import com.handmade.ecommerce.pricing.model.CartPricing;
import com.handmade.ecommerce.pricing.model.PricingRequest;

/**
 * Pricing Service Interface
 * Handles product pricing, discounts, and price calculations
 */
public interface PricingService {

    /**
     * Create a new price
     */
    Price createPrice(CreatePriceCommand command);

    /**
     * Get current price for a variant
     * Always returns fresh, real-time price
     */
    Money getCurrentPrice(String variantId);

    /**
     * Calculate complete cart pricing
     * Includes: subtotal, discounts, tax, shipping
     * Calculated fresh every time (Amazon-style)
     */
    CartPricing calculateCartPricing(PricingRequest request);

}
