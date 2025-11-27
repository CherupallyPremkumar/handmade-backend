package com.handmade.ecommerce.product.model;

/**
 * Variant Status Enum
 * Represents the lifecycle states of a product variant
 * Variants have independent state machine from Product
 */
public enum VariantStatus {
    /**
     * Variant is being created/edited
     */
    DRAFT,

    /**
     * Variant awaiting approval (e.g., significant price change)
     */
    PENDING_APPROVAL,

    /**
     * Variant is active and available for purchase
     */
    ACTIVE,

    /**
     * Variant is out of stock
     */
    OUT_OF_STOCK,

    /**
     * Variant temporarily disabled
     */
    INACTIVE,

    /**
     * Variant price change pending approval
     */
    PENDING_PRICE_CHANGE,

    /**
     * Variant permanently discontinued
     */
    DISCONTINUED
}
