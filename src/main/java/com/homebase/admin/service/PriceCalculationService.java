package com.homebase.admin.service;

import com.homebase.admin.entity.Product;
import com.homebase.admin.strategy.PricingStrategy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Service for calculating prices using Strategy Pattern
 * Handles both regular and sale pricing logic
 */
@Service
public class PriceCalculationService {

    private final PricingStrategy pricingStrategy;

    public PriceCalculationService(@Qualifier("salePricingStrategy") PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    /**
     * Calculate the current effective price for a product
     * Takes into account sale status
     */
    public BigDecimal getCurrentPrice(Product product) {
        return pricingStrategy.calculatePrice(product, 1);
    }

    /**
     * Calculate total price for a quantity
     */
    public BigDecimal calculateTotalPrice(Product product, int quantity) {
        return pricingStrategy.calculatePrice(product, quantity);
    }

    /**
     * Get the effective price (sale price if on sale, regular price otherwise)
     */
    public BigDecimal getEffectivePrice(Product product) {
        if (product.isOnSale() && product.getSalePrice() != null) {
            return product.getSalePrice();
        }
        return product.getPrice();
    }

    /**
     * Check if price has changed significantly
     */
    public boolean hasPriceChanged(BigDecimal oldPrice, BigDecimal newPrice) {
        if (oldPrice == null || newPrice == null) {
            return oldPrice != newPrice;
        }
        return oldPrice.compareTo(newPrice) != 0;
    }

    /**
     * Calculate price difference
     */
    public BigDecimal calculatePriceDifference(BigDecimal oldPrice, BigDecimal newPrice) {
        if (oldPrice == null || newPrice == null) {
            return BigDecimal.ZERO;
        }
        return newPrice.subtract(oldPrice);
    }
}
