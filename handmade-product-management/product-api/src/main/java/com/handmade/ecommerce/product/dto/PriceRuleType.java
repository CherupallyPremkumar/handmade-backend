package com.handmade.ecommerce.product.dto;

/**
 * Price Rule Type Enum
 * Represents different types of pricing rules
 */
public enum PriceRuleType {
    /**
     * Base price rule
     */
    BASE_PRICE,
    
    /**
     * Bulk discount (quantity-based)
     */
    BULK_DISCOUNT,
    
    /**
     * Seasonal pricing
     */
    SEASONAL,
    
    /**
     * Dynamic pricing (demand-based)
     */
    DYNAMIC,
    
    /**
     * Promotional pricing
     */
    PROMOTIONAL,
    
    /**
     * Customer segment pricing (VIP, wholesale, etc.)
     */
    CUSTOMER_SEGMENT,
    
    /**
     * Location-based pricing
     */
    LOCATION_BASED,
    
    /**
     * Time-based pricing (happy hour, etc.)
     */
    TIME_BASED
}
