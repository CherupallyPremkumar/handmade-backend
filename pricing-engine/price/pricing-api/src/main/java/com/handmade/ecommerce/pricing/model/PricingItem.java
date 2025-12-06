package com.handmade.ecommerce.pricing.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Individual item in pricing request
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PricingItem {

    /**
     * Variant ID
     */
    private String variantId;

    /**
     * Quantity
     */
    private Integer quantity;
}
