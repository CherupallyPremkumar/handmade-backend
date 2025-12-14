package com.handmade.ecommerce.rules.service;

import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.rules.dto.RuleContext;
import com.handmade.ecommerce.rules.dto.RuleResult;

/**
 * Rule Engine Abstraction Layer
 * 
 * Abstract interface for rule engines.
 * Implementations can use Easy Rules, Drools, or any other rule engine.
 * 
 * This abstraction allows:
 * - Easy migration from Easy Rules → Drools
 * - Unit testing with mock implementations
 * - A/B testing different rule engines
 * 
 * Usage:
 * - Today: EasyRulesEngine
 * - Later: DroolsEngine
 */
public interface RuleEngine {

    /**
     * Apply discount rules to the base price
     * 
     * @param basePrice The price before discounts
     * @param context Rule context with all relevant information
     * @return Rule result with final price and applied rules
     */
    RuleResult applyDiscountRules(Money basePrice, RuleContext context);

    /**
     * Apply tax rules to calculate tax amount
     * 
     * @param subtotal The subtotal after discounts
     * @param context Rule context with region info
     * @return Rule result with tax amount
     */
    RuleResult applyTaxRules(Money subtotal, RuleContext context);

    /**
     * Clear state for next calculation
     */
    void reset();
}
