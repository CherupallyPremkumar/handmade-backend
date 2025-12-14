package com.handmade.ecommerce.pricing;

import com.handmade.ecommerce.command.CreatePriceCommand;
import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.pricing.entity.Price;
import com.handmade.ecommerce.pricing.dto.CartPricing;
import com.handmade.ecommerce.pricing.dto.PriceCalculationResult;
import com.handmade.ecommerce.pricing.dto.PricingContext;
import com.handmade.ecommerce.pricing.dto.PricingRequest;

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
     * Calculate complete List of cart pricing
     * Includes: subtotal, discounts, tax, shipping
     * Calculated fresh every time (Amazon-style)
     */
    CartPricing calculateCartPricing(PricingRequest request);

    /**
     * Calculate complete price with full breakdown
     * Follows the pricing flow:
     * 1. Get base price
     * 2. Check regional override
     * 3. Convert currency
     * 4. Apply price rules
     * 5. Calculate tax
     * 6. Build result
     */
    PriceCalculationResult calculatePrice(PricingContext context);
}
