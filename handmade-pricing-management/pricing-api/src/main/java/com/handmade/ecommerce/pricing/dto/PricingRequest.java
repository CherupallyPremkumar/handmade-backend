package com.handmade.ecommerce.pricing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Request for calculating cart pricing
 * Generic DTO - not tied to Cart entity
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PricingRequest {

    /**
     * Customer ID (for personalized pricing)
     */
    private String customerId;

    /**
     * Items to price
     */
    private List<PricingItem> items;

}
