package com.handmade.ecommerce.promotion.model;

/**
 * PromotionTargetType - Enum
 * Defines what the promotion targets
 */
public enum PromotionTargetType {
    CATEGORY,      // Target all products in a category (e.g., "20% off all Pottery")
    PRODUCT,       // Target specific product(s)
    COLLECTION,    // Target all products in a collection (e.g., "Holiday Gifts")
    ARTISAN,       // Target all products from specific artisan
    GLOBAL         // Site-wide promotion (all products)
}
