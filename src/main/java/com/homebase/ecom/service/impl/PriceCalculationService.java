package com.homebase.ecom.service.impl;

import com.homebase.ecom.domain.PriceLine;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Clock;
import java.time.LocalDateTime;

/**
 * Service for calculating prices and handling price-related business logic.
 * Extracted from PriceLine domain model for better testability and separation
 * of concerns.
 */
@Service
public class PriceCalculationService {

    private final Clock clock;

    public PriceCalculationService(Clock clock) {
        this.clock = clock;
    }

    /**
     * Check if a price line is currently on sale
     */
    public boolean isOnSale(PriceLine priceLine) {
        if (priceLine == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now(clock);

        boolean inDateRange = isWithinSalePeriod(priceLine, now);
        boolean hasDiscount = hasValidDiscount(priceLine);
        boolean inSaleState = "ON_SALE".equals(priceLine.getCurrentState().getStateId());

        return inDateRange && hasDiscount && inSaleState;
    }

    /**
     * Calculate the final price considering sales and discounts
     */
    public BigDecimal calculateFinalPrice(PriceLine priceLine) {
        if (priceLine == null || priceLine.getAmount() == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal baseAmount = priceLine.getAmount();

        if (!isOnSale(priceLine)) {
            return baseAmount;
        }

        // Priority 1: Use explicit sale amount if set
        if (priceLine.getSaleAmount() != null) {
            return priceLine.getSaleAmount();
        }

        // Priority 2: Apply discount percentage
        if (priceLine.getDiscountPercentage() != null
                && priceLine.getDiscountPercentage().compareTo(BigDecimal.ZERO) > 0) {
            return applyDiscountPercentage(baseAmount, priceLine.getDiscountPercentage());
        }

        return baseAmount;
    }

    /**
     * Calculate savings amount (base price - final price)
     */
    public BigDecimal calculateSavings(PriceLine priceLine) {
        if (priceLine == null || !isOnSale(priceLine)) {
            return BigDecimal.ZERO;
        }

        BigDecimal baseAmount = priceLine.getAmount() != null ? priceLine.getAmount() : BigDecimal.ZERO;
        BigDecimal finalPrice = calculateFinalPrice(priceLine);

        return baseAmount.subtract(finalPrice).max(BigDecimal.ZERO);
    }

    /**
     * Calculate savings percentage
     */
    public BigDecimal calculateSavingsPercentage(PriceLine priceLine) {
        if (priceLine == null || priceLine.getAmount() == null
                || priceLine.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal savings = calculateSavings(priceLine);
        BigDecimal baseAmount = priceLine.getAmount();

        return savings.divide(baseAmount, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Check if the sale period is valid for the current time
     */
    private boolean isWithinSalePeriod(PriceLine priceLine, LocalDateTime now) {
        LocalDateTime startDate = priceLine.getSaleStartDate();
        LocalDateTime endDate = priceLine.getSaleEndDate();

        // If no dates set, consider it always valid
        if (startDate == null || endDate == null) {
            return true;
        }

        return now.isAfter(startDate) && now.isBefore(endDate);
    }

    /**
     * Check if the price line has a valid discount
     */
    private boolean hasValidDiscount(PriceLine priceLine) {
        BigDecimal baseAmount = priceLine.getAmount();
        BigDecimal saleAmount = priceLine.getSaleAmount();
        BigDecimal discountPercentage = priceLine.getDiscountPercentage();

        // Has explicit sale amount that's less than base
        boolean hasSaleAmount = saleAmount != null
                && baseAmount != null
                && saleAmount.compareTo(baseAmount) < 0;

        // Has discount percentage greater than zero
        boolean hasDiscountPercentage = discountPercentage != null
                && discountPercentage.compareTo(BigDecimal.ZERO) > 0;

        return hasSaleAmount || hasDiscountPercentage;
    }

    /**
     * Apply discount percentage to base amount
     */
    private BigDecimal applyDiscountPercentage(BigDecimal baseAmount, BigDecimal discountPercentage) {
        if (discountPercentage == null || discountPercentage.compareTo(BigDecimal.ZERO) == 0) {
            return baseAmount;
        }

        BigDecimal discountFactor = BigDecimal.ONE.subtract(
                discountPercentage.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));

        return baseAmount.multiply(discountFactor).setScale(2, RoundingMode.HALF_UP);
    }
}
