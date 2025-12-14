package com.handmade.ecommerce.catalog.model;

/**
 * Inventory Strategy Enum
 * Determines how inventory is managed based on category
 */
public enum InventoryStrategy {
    /**
     * Inventory tracked by variant
     * Used for: Electronics, Clothes, Jewelry
     */
    VARIANT_BASED,
    
    /**
     * Inventory tracked at product level
     * Used for: Food, Perishables
     */
    PRODUCT_BASED,
    
    /**
     * Made to order, no stock tracking
     * Used for: Handmade, Custom items
     */
    MADE_TO_ORDER,
    
    /**
     * Digital products, infinite availability
     * Used for: Downloads, Software, eBooks
     */
    DIGITAL,
    
    /**
     * Consignment inventory
     * Used for: Third-party managed stock
     */
    CONSIGNMENT
}
