package com.handmade.ecommerce.review.model;

/**
 * Review Status Enum
 * Represents the lifecycle states of a product review
 */
public enum ReviewStatus {
    /**
     * Review submitted, awaiting moderation
     */
    PENDING,
    
    /**
     * Review approved and visible
     */
    APPROVED,
    
    /**
     * Review rejected (spam, inappropriate, etc.)
     */
    REJECTED,



    
    /**
     * Review flagged for admin attention
     */
    FLAGGED
}
