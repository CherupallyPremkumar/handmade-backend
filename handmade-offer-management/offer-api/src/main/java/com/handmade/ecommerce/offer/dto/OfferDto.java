package com.handmade.ecommerce.offer.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * Offer Abstraction Stub (Phase 2 - Amazon Alignment)
 * 
 * Logic: Offer = Product Variant + Seller + Price + Inventory
 * 
 * This entity unifies product details with seller-specific attributes,
 * market pricing, and available stock.
 */
@Data
public class OfferDto {
    private String offerId;
    
    // Product Variant Reference
    private String productId;
    private String variantId;
    
    // Seller & Market Reference
    private String sellerId;
    private String regionId;
    
    // Price Details
    private BigDecimal price;
    private String currency;
    
    // Offer Lifecycle State
    private String state; // e.g., DRAFT, ACTIVE, SUSPENDED
    private String activationReason;
    private String suspensionReason;
    
    private String offerCode;
}
