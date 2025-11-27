package com.handmade.ecommerce.pricing.model;

/**
 * Adjustment Type Enum
 * Represents how price adjustments are applied
 */
public enum AdjustmentType {
    /**
     * Percentage off (e.g., 10% off)
     */
    PERCENTAGE_OFF,
    
    /**
     * Fixed amount off (e.g., $5 off)
     */
    FIXED_AMOUNT_OFF,
    
    /**
     * Fixed price (e.g., set price to $99)
     */
    FIXED_PRICE,
    
    /**
     * Percentage markup (e.g., 20% markup)
     */
    PERCENTAGE_MARKUP,
    
    /**
     * Fixed amount markup (e.g., add $10)
     */
    FIXED_AMOUNT_MARKUP
}
