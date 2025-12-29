package com.handmade.ecommerce.pricing.dto;

import com.handmade.ecommerce.core.model.Money;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Pricing Exchange Object
 * Carries data through the OWIZ pricing flow
 */
@Getter
@Setter
public class PricingExchange {

    // Input
    private String variantId;
    private String productId;
    private Integer quantity = 1;
    private String regionCode;
    private String targetCurrency;
    private String customerId;
    private String customerSegment;
    private boolean firstOrder = false; // Is this customer's first order?

    // Step 1: Base Price
    private Money basePrice;
    private String baseCurrency;

    // Step 2: Regional Override
    private boolean hasRegionalOverride = false;
    private Money regionalPrice;

    // Step 3: Currency Conversion
    private Money convertedPrice;
    private boolean currencyConverted = false;

    // Step 4: Applied Rules
    private List<AppliedRule> appliedRules = new ArrayList<>();
    private Money discountedPrice;
    private Money totalDiscount;

    // Step 5: Tax
    private Money taxAmount;
    private String taxDescription;

    // Step 6: Final Result
    private Money total;
    private PriceCalculationResult result;

    /**
     * Get effective price (regional or base)
     */
    public Money getEffectivePrice() {
        return hasRegionalOverride && regionalPrice != null ? regionalPrice : basePrice;
    }

    /**
     * Get price after conversion (or effective if not converted)
     */
    public Money getPriceForRules() {
        return currencyConverted && convertedPrice != null ? convertedPrice : getEffectivePrice();
    }
}
