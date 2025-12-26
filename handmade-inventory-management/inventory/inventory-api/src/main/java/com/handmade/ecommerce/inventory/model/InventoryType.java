package com.handmade.ecommerce.inventory.model;

/**
 * Inventory Type Enum
 * Determines the inventory management strategy based on product category
 */
public enum InventoryType {
    /**
     * Inventory tracked by variant (Electronics, Clothes)
     * Each variant has its own stock level
     */
    VARIANT_BASED,

    /**
     * Inventory tracked at product level (Food, Perishables)
     * Includes expiry date tracking
     */
    PRODUCT_BASED,

    /**
     * Made to order items (Handmade, Custom)
     * No stock tracking, only lead time
     */
    MADE_TO_ORDER,

    /**
     * Digital products (Downloads, Software)
     * Infinite availability
     */
    DIGITAL
}
