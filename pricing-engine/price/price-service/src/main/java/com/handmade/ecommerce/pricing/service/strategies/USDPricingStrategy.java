package com.handmade.ecommerce.pricing.service.strategies;

import com.handmade.ecommerce.core.model.Money;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * USA (USD) pricing strategy
 * Handles USA-specific pricing, tax, and inventory
 * 
 * This is an EXAMPLE - just create a new class like this to add a new region!
 */
@Component
public class USDPricingStrategy implements PricingStrategy {

    @Override
    public String getRegionCode() {
        return "US";
    }

    @Override
    public Money getCurrentPrice(String variantId) {
        // TODO: Fetch from database based on variant and region
        // For now, return dummy price
        return new Money(new BigDecimal("12.99"), "USD");
    }

    @Override
    public Money calculateTax(Money amount) {
        // USA: State-specific tax (example: 7.25% for California)
        BigDecimal taxRate = new BigDecimal("0.0725");
        BigDecimal taxAmount = amount.getAmount().multiply(taxRate);
        return new Money(taxAmount, amount.getCurrency());
    }

    @Override
    public int getAvailableStock(String variantId) {
        // TODO: Fetch from inventory system for USA region
        return 50;
    }
}
