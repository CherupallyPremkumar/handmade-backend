package com.handmade.ecommerce.product.domain.model;

/**
 * Product Status Enum
 * Represents the lifecycle states of a product
 */
public enum ProductStatus {
    /**
     * Product is being created/edited by seller
     */
    DRAFT,

    /**
     * Product submitted and awaiting admin review
     */
    PENDING_REVIEW,

    /**
     * Product needs changes before approval
     */
    NEEDS_REVISION,

    /**
     * Product approved by admin, ready to publish
     */
    APPROVED,

    /**
     * Product is live and visible to customers
     */
    ACTIVE,

    /**
     * Product temporarily disabled
     */
    INACTIVE,

    /**
     * All variants are out of stock
     */
    OUT_OF_STOCK,

    /**
     * Product rejected by admin
     */
    REJECTED,

    /**
     * Product permanently discontinued
     */
    DISCONTINUED
}
