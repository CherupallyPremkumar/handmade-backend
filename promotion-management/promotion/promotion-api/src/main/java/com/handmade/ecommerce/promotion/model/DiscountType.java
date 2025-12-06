package com.handmade.ecommerce.promotion.model;

/**
 * DiscountType - Enum
 * Types of discounts that can be applied
 */
public enum DiscountType {
    PERCENTAGE,     // Percentage off (e.g., 20% off)
    FIXED_AMOUNT,   // Fixed amount off (e.g., $10 off)
    BUY_X_GET_Y,    // Buy X get Y free (e.g., Buy 2 get 1 free)
    FREE_SHIPPING   // Free shipping promotion
}
