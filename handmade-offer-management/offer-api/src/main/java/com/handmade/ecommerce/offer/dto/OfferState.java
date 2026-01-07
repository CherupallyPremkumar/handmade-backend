package com.handmade.ecommerce.offer.dto;

/**
 * Core commercial states for an Offer.
 */
public enum OfferState {
    /** Created by seller, not ready for sale */
    DRAFT,
    
    /** Waiting for inventory, compliance, or pricing validation */
    PENDING_ACTIVATION,
    
    /** Buyable in that region */
    ACTIVE,
    
    /** Temporarily unavailable (no stock, compliance issue, seller holiday) */
    SUSPENDED,
    
    /** Permanently removed */
    CLOSED
}
