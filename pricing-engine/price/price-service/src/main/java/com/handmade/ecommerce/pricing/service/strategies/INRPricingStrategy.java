package com.handmade.ecommerce.pricing.service.strategies;

import com.handmade.ecommerce.core.model.Money;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * India (INR) pricing strategy
 * Handles India-specific pricing, tax, and inventory
 */
@Component
public class INRPricingStrategy implements PricingStrategy {

    @Override
    public String getRegionCode() {
        return "IN";
    }

    @Override
    public Money getCurrentPrice(String variantId) {
        // TODO: Fetch from database based on variant and region
        // For now, return dummy price
        return new Money(new BigDecimal("999.00"), "INR");
    }

    @Override
    public Money calculateTax(Money amount) {
        // India GST: 18%
        BigDecimal taxRate = new BigDecimal("0.18");
        BigDecimal taxAmount = amount.getAmount().multiply(taxRate);
        return new Money(taxAmount, amount.getCurrency());
    }

    @Override
    public int getAvailableStock(String variantId) {
        // TODO: Fetch from inventory system for India region
        return 100;
    }
}
