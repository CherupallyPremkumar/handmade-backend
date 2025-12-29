package com.handmade.ecommerce.pricing.usd;

import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.pricing.service.PricingStrategy;
import com.handmade.ecommerce.pricing.service.store.PriceEntityStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * USA (USD) pricing strategy
 * Handles USA-specific tax calculation (state-based)
 * TODO: Make tax rate configurable per state
 */
@Component
public class USDPricingStrategy implements PricingStrategy {
    
    private static final Logger logger = LoggerFactory.getLogger(USDPricingStrategy.class);
    
    @Autowired
    protected PriceEntityStore priceEntityStore;

    @Override
    public String getRegionCode() {
        return "US";
    }

    @Override
    public Money getCurrentPrice(String variantId) {
        try {
            var price = priceEntityStore.getPriceByVariantId(variantId);
            logger.debug("Found price for variant {} in USA: {}", variantId, price.getCurrentPrice());
            return new Money(price.getCurrentPrice(), price.getBaseCurrency());
        } catch (Exception e) {
            logger.error("Error fetching price for variant {}: {}", variantId, e.getMessage());
            throw new RuntimeException("Failed to fetch price for variant: " + variantId, e);
        }
    }

    @Override
    public Money calculateTax(Money amount) {
        // USA: State-specific tax (example: 7.25% for California)
        // TODO: Make this configurable per state
        BigDecimal taxRate = new BigDecimal("0.0725");
        BigDecimal taxAmount = amount.getAmount().multiply(taxRate);
        return new Money(taxAmount, amount.getCurrency());
    }
}
