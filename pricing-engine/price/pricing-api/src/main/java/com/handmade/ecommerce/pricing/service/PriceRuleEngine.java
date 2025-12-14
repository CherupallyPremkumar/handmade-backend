package com.handmade.ecommerce.pricing.service;

import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.pricing.dto.AppliedRule;
import com.handmade.ecommerce.pricing.dto.PricingContext;
import com.handmade.ecommerce.pricing.entity.PriceRule;

import java.util.List;

/**
 * Price Rule Engine Interface
 * Evaluates and applies price rules (discounts, markups, etc.)
 */
public interface PriceRuleEngine {

    /**
     * Apply all applicable rules to base price
     * 
     * @param basePrice Original price
     * @param rules     List of price rules to evaluate
     * @param context   Pricing context with quantity, customer info, etc.
     * @return Final price after all rules applied
     */
    Money applyRules(Money basePrice, List<PriceRule> rules, PricingContext context);

    /**
     * Evaluate which rules apply without modifying price
     * 
     * @param rules   List of price rules to evaluate
     * @param context Pricing context
     * @return List of rules that would apply
     */
    List<AppliedRule> evaluateRules(List<PriceRule> rules, PricingContext context);

    /**
     * Calculate total discount from applied rules
     * 
     * @param basePrice    Original price
     * @param appliedRules Rules that were applied
     * @return Total discount amount
     */
    Money calculateDiscount(Money basePrice, List<AppliedRule> appliedRules);
}
