package com.handmade.ecommerce.pricing.model;

/**
 * Price Type Enum
 * Represents different types of pricing
 */
public enum PriceType {
    /**
     * Standard regular price
     */
    STANDARD,
    
    /**
     * Sale/discounted price
     */
    SALE,
    
    /**
     * Clearance price (final sale)
     */
    CLEARANCE,
    
    /**
     * Promotional price (limited time)
     */
    PROMOTIONAL,
    
    /**
     * Dynamic pricing (algorithm-based)
     */
    DYNAMIC
}
