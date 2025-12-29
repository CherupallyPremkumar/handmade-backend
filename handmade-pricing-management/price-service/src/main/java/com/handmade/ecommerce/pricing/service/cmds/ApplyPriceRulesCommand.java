package com.handmade.ecommerce.pricing.service.cmds;

import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.pricing.dto.AppliedRule;
import com.handmade.ecommerce.pricing.dto.PricingExchange;
import com.handmade.ecommerce.rules.dto.AppliedRuleInfo;
import com.handmade.ecommerce.rules.dto.RuleContext;
import com.handmade.ecommerce.rules.dto.RuleResult;
import com.handmade.ecommerce.rules.service.RuleEngine;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

/**
 * OWIZ Command: Step 4 - Apply Price Rules
 * 
 * Uses the RuleEngine from rules module for discount calculation.
 * Rule logic has been extracted to the rules module.
 */
@Component("applyPriceRulesCommand")
public class ApplyPriceRulesCommand implements Command<PricingExchange> {

    private static final Logger logger = LoggerFactory.getLogger(ApplyPriceRulesCommand.class);

    @Autowired
    private RuleEngine ruleEngine;

    @Override
    public void execute(PricingExchange exchange) throws Exception {
        Money priceForRules = exchange.getPriceForRules();

        logger.info("Step 4: Applying price rules. Base: {} {}",
                priceForRules.getAmount(), priceForRules.getCurrency());

        // Build context for rules module
        RuleContext context = RuleContext.builder()
                .variantId(exchange.getVariantId())
                .productId(exchange.getProductId())
                .quantity(exchange.getQuantity())
                .regionCode(exchange.getRegionCode())
                .customerId(exchange.getCustomerId())
                .customerSegment(exchange.getCustomerSegment())
                .firstOrder(exchange.isFirstOrder())
                .currency(exchange.getTargetCurrency())
                .build();

        // Apply discount rules using rules module
        RuleResult result = ruleEngine.applyDiscountRules(priceForRules, context);

        // Store results - convert AppliedRuleInfo to AppliedRule
        exchange.setDiscountedPrice(result.getFinalPrice());
        exchange.setTotalDiscount(result.getTotalDiscount() != null
                ? result.getTotalDiscount()
                : new Money(BigDecimal.ZERO, priceForRules.getCurrency()));

        exchange.setAppliedRules(result.getAppliedRules().stream()
                .map(this::convertToAppliedRule)
                .collect(Collectors.toList()));

        logger.info("Price after rules: {} {} (discount: {})",
                result.getFinalPrice().getAmount(),
                result.getFinalPrice().getCurrency(),
                exchange.getTotalDiscount().getAmount());
    }

    private AppliedRule convertToAppliedRule(AppliedRuleInfo info) {
        return AppliedRule.builder()
                .ruleId(info.getRuleId())
                .ruleName(info.getRuleName())
                .ruleType(info.getRuleType())
                .percentage(info.getPercentage())
                .adjustment(info.getAdjustment())
                .description(info.getDescription())
                .build();
    }
}
