package com.handmade.ecommerce.pricing.inr;

import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.pricing.service.PricingStrategy;
import com.handmade.ecommerce.pricing.service.store.PriceEntityStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * India (INR) pricing strategy
 * Handles India-specific tax calculation (GST: 18%)
 */
@Component
public class INRPricingStrategy implements PricingStrategy {
    
    private static final Logger logger = LoggerFactory.getLogger(INRPricingStrategy.class);
    
    @Autowired
    protected PriceEntityStore priceEntityStore;

    @Override
    public String getRegionCode() {
        return "IN";
    }

    @Override
    public Money getCurrentPrice(String variantId) {
        try {
            var price = priceEntityStore.getPriceByVariantId(variantId);
            logger.debug("Found price for variant {} in India: {}", variantId, price.getCurrentPrice());
            return new Money(price.getCurrentPrice(), price.getBaseCurrency());
        } catch (Exception e) {
            logger.error("Error fetching price for variant {}: {}", variantId, e.getMessage());
            throw new RuntimeException("Failed to fetch price for variant: " + variantId, e);
        }
    }

    @Override
    public Money calculateTax(Money amount) {
        // India GST: 18%
        BigDecimal taxRate = new BigDecimal("0.18");
        BigDecimal taxAmount = amount.getAmount().multiply(taxRate);
        return new Money(taxAmount, amount.getCurrency());
    }
}
