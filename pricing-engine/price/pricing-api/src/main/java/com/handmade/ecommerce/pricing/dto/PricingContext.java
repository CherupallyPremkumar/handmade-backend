package com.handmade.ecommerce.pricing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Pricing context for price calculation
 * Contains all information needed to calculate price
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PricingContext {

    /**
     * Product ID
     */
    private String productId;

    /**
     * Variant ID
     */
    private String variantId;

    /**
     * Quantity being purchased
     */
    private Integer quantity;

    /**
     * Customer ID (for personalized pricing)
     */
    private String customerId;

    /**
     * Region code (IN, US, EU)
     */
    private String regionCode;

    /**
     * Target currency for display
     */
    private String targetCurrency;

    /**
     * Customer segment (REGULAR, VIP, PREMIUM)
     */
    private String customerSegment;

    /**
     * Timestamp for time-based pricing
     */
    private LocalDateTime timestamp;

    /**
     * Is this the customer's first order?
     */
    private boolean firstOrder = false;

    /**
     * Default quantity to 1 if not set
     */
    public Integer getQuantity() {
        return quantity != null ? quantity : 1;
    }

    /**
     * Default timestamp to now if not set
     */
    public LocalDateTime getTimestamp() {
        return timestamp != null ? timestamp : LocalDateTime.now();
    }
}
