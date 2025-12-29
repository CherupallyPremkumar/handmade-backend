package com.handmade.ecommerce.rules.service.engine;

import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.rules.configuration.dao.DiscountRuleRepository;
import com.handmade.ecommerce.rules.configuration.dao.TaxRuleRepository;
import com.handmade.ecommerce.rules.dto.AppliedRuleInfo;
import com.handmade.ecommerce.rules.dto.RuleContext;
import com.handmade.ecommerce.rules.dto.RuleResult;
import com.handmade.ecommerce.rules.entity.DiscountRule;
import com.handmade.ecommerce.rules.entity.TaxRule;
import com.handmade.ecommerce.rules.service.RuleEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Easy Rules Implementation of RuleEngine
 * 
 * Loads rules from database and evaluates them dynamically.
 * Rules are stored in neutral JSON format - not Easy Rules specific.
 * 
 * Migration to Drools:
 * 1. Create DroolsRuleEngine implementing RuleEngine
 * 2. Change bean wiring in RuleEngineConfiguration
 * 3. No other code changes needed!
 */
@Service("easyRulesEngine")
public class EasyRulesEngine implements RuleEngine {

    private static final Logger logger = LoggerFactory.getLogger(EasyRulesEngine.class);

    @Autowired
    private DiscountRuleRepository discountRuleRepository;

    @Autowired
    private TaxRuleRepository taxRuleRepository;

    // Thread-local state for current calculation
    private ThreadLocal<RuleResult> currentResultHolder = ThreadLocal.withInitial(RuleResult::new);

    @Override
    public RuleResult applyDiscountRules(Money basePrice, RuleContext context) {
        reset();
        RuleResult result = currentResultHolder.get();
        result.setAppliedRules(new ArrayList<>());

        logger.info("EasyRules: Applying discount rules for variant: {}", context.getVariantId());

        // Load rules from database
        LocalDateTime now = LocalDateTime.now();
        List<DiscountRule> rules = discountRuleRepository.findActiveGlobalRules(now);

        logger.debug("Found {} active discount rules", rules.size());

        Money currentPrice = basePrice;
        BigDecimal totalDiscountAmount = BigDecimal.ZERO;

        for (DiscountRule rule : rules) {
            if (evaluateCondition(rule, context)) {
                BigDecimal discountAmount = calculateDiscount(currentPrice, rule);

                // Cap if needed
                if (rule.getMaxDiscountAmount() != null && 
                    discountAmount.compareTo(rule.getMaxDiscountAmount()) > 0) {
                    discountAmount = rule.getMaxDiscountAmount();
                }

                // Apply
                currentPrice = new Money(
                    currentPrice.getAmount().subtract(discountAmount),
                    currentPrice.getCurrency()
                );
                totalDiscountAmount = totalDiscountAmount.add(discountAmount);

                // Track
                result.getAppliedRules().add(AppliedRuleInfo.builder()
                    .ruleId(rule.getId().toString())
                    .ruleName(rule.getRuleName())
                    .ruleType(rule.getRuleType())
                    .adjustment(discountAmount)
                    .description(rule.getDescription())
                    .build());

                logger.info("Applied rule '{}': {} off", rule.getRuleName(), discountAmount);

                // Stop if not stackable
                if (!rule.getStackable()) {
                    break;
                }
            }
        }

        result.setFinalPrice(currentPrice);
        result.setTotalDiscount(new Money(totalDiscountAmount, basePrice.getCurrency()));

        return result;
    }

    @Override
    public RuleResult applyTaxRules(Money subtotal, RuleContext context) {
        RuleResult result = currentResultHolder.get();
        String regionCode = context.getRegionCode();
        LocalDateTime now = LocalDateTime.now();

        logger.info("EasyRules: Applying tax rules for region: {}", regionCode);

        // Look up tax rule from database
        Optional<TaxRule> taxRuleOpt = taxRuleRepository.findByRegionCode(regionCode, now);

        if (taxRuleOpt.isEmpty()) {
            logger.warn("No tax rule found for region: {}", regionCode);
            result.setTaxAmount(new Money(BigDecimal.ZERO, subtotal.getCurrency()));
            result.setTaxDescription("No tax");
            result.setTaxRate(BigDecimal.ZERO);
            return result;
        }

        TaxRule taxRule = taxRuleOpt.get();
        BigDecimal rate = taxRule.getTaxRate().divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        BigDecimal taxAmount = subtotal.getAmount().multiply(rate);

        result.setTaxAmount(new Money(taxAmount, subtotal.getCurrency()));
        result.setTaxDescription(String.format("%s (%s%%)", taxRule.getTaxName(), taxRule.getTaxRate()));
        result.setTaxRate(rate);

        logger.info("Applied tax: {} at {}%", taxRule.getTaxName(), taxRule.getTaxRate());

        return result;
    }

    @Override
    public void reset() {
        currentResultHolder.set(new RuleResult());
    }

    /**
     * Evaluate rule condition against context
     * Condition is stored as neutral format - not Easy Rules specific!
     */
    private boolean evaluateCondition(DiscountRule rule, RuleContext context) {
        String field = rule.getConditionField();
        String operator = rule.getConditionOperator();
        String value = rule.getConditionValue();

        try {
            switch (field.toLowerCase()) {
                case "quantity":
                    return evaluateNumeric(context.getQuantity(), operator, value);
                case "customersegment":
                    return evaluateString(context.getCustomerSegment(), operator, value);
                case "isfirstorder":
                    return evaluateBoolean(context.isFirstOrder(), operator, value);
                case "regioncode":
                    return evaluateString(context.getRegionCode(), operator, value);
                case "all":
                case "always":
                    return true;
                default:
                    logger.warn("Unknown condition field: {}", field);
                    return false;
            }
        } catch (Exception e) {
            logger.error("Error evaluating condition: {}", e.getMessage());
            return false;
        }
    }

    private boolean evaluateNumeric(Integer actual, String operator, String value) {
        if (actual == null) return false;
        int expected = Integer.parseInt(value);
        switch (operator) {
            case ">=": return actual >= expected;
            case ">": return actual > expected;
            case "<=": return actual <= expected;
            case "<": return actual < expected;
            case "==": return actual == expected;
            default: return false;
        }
    }

    private boolean evaluateString(String actual, String operator, String value) {
        if (actual == null) return false;
        switch (operator.toLowerCase()) {
            case "==":
            case "equals": return actual.equalsIgnoreCase(value);
            case "!=": return !actual.equalsIgnoreCase(value);
            case "in": return value.contains(actual);
            default: return false;
        }
    }

    private boolean evaluateBoolean(boolean actual, String operator, String value) {
        boolean expected = Boolean.parseBoolean(value);
        switch (operator) {
            case "==": return actual == expected;
            case "!=": return actual != expected;
            default: return false;
        }
    }

    private BigDecimal calculateDiscount(Money price, DiscountRule rule) {
        if ("PERCENTAGE".equalsIgnoreCase(rule.getDiscountType())) {
            return price.getAmount()
                .multiply(rule.getDiscountValue())
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        } else if ("FIXED_AMOUNT".equalsIgnoreCase(rule.getDiscountType())) {
            return rule.getDiscountValue();
        }
        return BigDecimal.ZERO;
    }
}
