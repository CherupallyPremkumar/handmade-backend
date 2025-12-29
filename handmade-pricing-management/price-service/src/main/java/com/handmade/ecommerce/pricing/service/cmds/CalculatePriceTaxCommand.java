package com.handmade.ecommerce.pricing.service.cmds;

import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.pricing.dto.PricingExchange;
import com.handmade.ecommerce.rules.dto.RuleContext;
import com.handmade.ecommerce.rules.dto.RuleResult;
import com.handmade.ecommerce.rules.service.RuleEngine;
import org.chenile.owiz.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * OWIZ Command: Step 5 - Calculate Tax
 * 
 * Uses the RuleEngine from rules module for tax calculation.
 * Tax logic has been extracted to the rules module.
 */
@Component("calculatePriceTaxCommand")
public class CalculatePriceTaxCommand implements Command<PricingExchange> {

    private static final Logger logger = LoggerFactory.getLogger(CalculatePriceTaxCommand.class);

    @Autowired
    private RuleEngine ruleEngine;

    @Override
    public void execute(PricingExchange exchange) throws Exception {
        String regionCode = exchange.getRegionCode();
        Money subtotal = exchange.getDiscountedPrice();

        logger.info("Step 5: Calculating tax for region: {}", regionCode);

        // Build context for rules module
        RuleContext context = RuleContext.builder()
                .variantId(exchange.getVariantId())
                .regionCode(regionCode)
                .build();

        // Apply tax rules using rules module
        RuleResult result = ruleEngine.applyTaxRules(subtotal, context);

        // Store results
        exchange.setTaxAmount(result.getTaxAmount());
        exchange.setTaxDescription(result.getTaxDescription());

        logger.info("Tax calculated: {} {} - {}",
                result.getTaxAmount().getAmount(),
                result.getTaxAmount().getCurrency(),
                result.getTaxDescription());
    }
}
