package com.handmade.ecommerce.promotion.model;

/**
 * Promotion Type Enum
 * Represents different types of promotions
 */
public enum PromotionType {
    /**
     * Percentage discount (e.g., 20% off)
     */
    PERCENTAGE_OFF,
    
    /**
     * Fixed amount discount (e.g., $10 off)
     */
    FIXED_AMOUNT_OFF,
    
    /**
     * Buy X Get Y free
     */
    BUY_X_GET_Y,
    
    /**
     * Free shipping
     */
    FREE_SHIPPING,
    
    /**
     * Bundle discount
     */
    BUNDLE,
    
    /**
     * Flash sale
     */
    FLASH_SALE
}
