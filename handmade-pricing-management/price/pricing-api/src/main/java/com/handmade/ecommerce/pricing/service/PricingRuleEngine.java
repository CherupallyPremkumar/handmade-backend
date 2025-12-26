package com.handmade.ecommerce.pricing.service;

import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.pricing.dto.AppliedRule;
import com.handmade.ecommerce.pricing.dto.PricingContext;

import java.util.List;

/**
 * Rule Engine Abstraction Layer
 * 
 * Abstract interface for pricing rule engines.
 * Implementations can use Easy Rules, Drools, or any other rule engine.
 * 
 * This abstraction allows:
 * - Easy migration from Easy Rules → Drools
 * - Unit testing with mock implementations
 * - A/B testing different rule engines
 * 
 * Usage:
 * - Today: EasyRulesPricingEngine
 * - Later: DroolsPricingEngine
 */
public interface PricingRuleEngine {

    /**
     * Apply all applicable pricing rules to the base price
     * 
     * @param basePrice The price before discounts
     * @param context   Pricing context with all relevant information
     * @return Final price after applying all rules
     */
    Money apply(Money basePrice, PricingContext context);

    /**
     * Get all rules that were applied
     * 
     * @return List of applied rules with details
     */
    List<AppliedRule> getAppliedRules();

    /**
     * Get total discount amount
     * 
     * @return Total discount as Money
     */
    Money getTotalDiscount();

    /**
     * Clear state for next calculation
     */
    void reset();
}
