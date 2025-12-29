package com.handmade.ecommerce.pricing.dto;

import com.handmade.ecommerce.core.model.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Complete price calculation result
 * Contains full breakdown of pricing calculation
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceCalculationResult {

    /**
     * Original base price (in base currency, usually USD)
     */
    private Money basePrice;

    /**
     * Price after currency conversion
     */
    private Money convertedPrice;

    /**
     * List of applied price rules
     */
    private List<AppliedRule> appliedRules;

    /**
     * Total discount amount
     */
    private Money discount;

    /**
     * Subtotal after discounts
     */
    private Money subtotal;

    /**
     * Tax amount
     */
    private Money tax;

    /**
     * Final total (subtotal + tax)
     */
    private Money total;

    /**
     * Target currency code
     */
    private String currency;

    /**
     * Tax rate applied
     */
    private String taxDescription;

    /**
     * Get total savings
     */
    public Money getTotalSavings() {
        return discount;
    }

    /**
     * Get effective price per unit
     */
    public Money getEffectivePrice() {
        return total;
    }
}
