package com.handmade.ecommerce.pricing.model;

import com.handmade.ecommerce.core.model.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Complete cart pricing breakdown
 * Returned by PricingService
 */
@Getter
@Setter
@AllArgsConstructor
public class CartPricing {

    /**
     * Subtotal (sum of line items)
     */
    private Money subtotal;

    /**
     * Discount amount (promotions, coupons)
     */
    private Money discount;

    /**
     * Tax amount
     */
    private Money tax;

    /**
     * Shipping cost
     */
    private Money shipping;

    /**
     * Final total
     */
    private Money total;
}
