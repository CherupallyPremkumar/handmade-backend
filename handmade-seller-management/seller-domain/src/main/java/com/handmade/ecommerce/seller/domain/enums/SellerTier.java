package com.handmade.ecommerce.seller.domain.enums;

/**
 * Seller performance tiers
 * Based on sales volume and customer satisfaction
 */
public enum SellerTier {
    /**
     * New seller or low volume
     * < $10K/month
     */
    BRONZE,
    
    /**
     * Established seller
     * $10K - $50K/month
     */
    SILVER,
    
    /**
     * High-performing seller
     * $50K - $250K/month
     */
    GOLD,
    
    /**
     * Top-tier seller
     * > $250K/month
     */
    PLATINUM
}
