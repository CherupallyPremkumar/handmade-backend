package com.handmade.ecommerce.seller.domain.enums;

/**
 * Seller types aligned with commission tiers from platform-management
 */
public enum SellerType {
    /**
     * Individual artisan/craftsperson
     * Commission: 3%
     */
    ARTISAN,
    
    /**
     * Home-based small business
     * Commission: 4%
     */
    HOME_MAKER,
    
    /**
     * Registered small business
     * Commission: 5%
     */
    SMALL_BUSINESS,
    
    /**
     * Large enterprise seller
     * Commission: 6%
     */
    ENTERPRISE,
    
    /**
     * Dropshipper/reseller
     * Commission: 7%
     */
    DROPSHIPPER
}
