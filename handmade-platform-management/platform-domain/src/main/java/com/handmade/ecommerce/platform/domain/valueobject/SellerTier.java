package com.handmade.ecommerce.platform.domain.valueobject;

/**
 * Seller Tier Enum
 * Represents different seller categories with different commission rates
 */
public enum SellerTier {
    /**
     * Individual artisan - highest commission (10%)
     * Small volume, handcrafted items
     */
    INDIVIDUAL_ARTISAN,
    
    /**
     * Home-based maker - medium-high commission (8%)
     * Small to medium volume, home production
     */
    HOME_BASED_MAKER,
    
    /**
     * Small business - medium commission (6%)
     * Medium volume, established business
     */
    SMALL_BUSINESS,
    
    /**
     * Enterprise seller - lowest commission (5%)
     * High volume, large-scale production
     */
    ENTERPRISE_SELLER,
    
    /**
     * Dropshipper - custom commission
     * Third-party fulfillment
     */
    DROPSHIPPER;
    
    /**
     * Get display name for UI
     */
    public String getDisplayName() {
        return switch (this) {
            case INDIVIDUAL_ARTISAN -> "Individual Artisan";
            case HOME_BASED_MAKER -> "Home-Based Maker";
            case SMALL_BUSINESS -> "Small Business";
            case ENTERPRISE_SELLER -> "Enterprise Seller";
            case DROPSHIPPER -> "Dropshipper";
        };
    }
    
    /**
     * Parse from string (for backward compatibility)
     */
    public static SellerTier fromString(String tierString) {
        if (tierString == null) {
            return INDIVIDUAL_ARTISAN; // Default
        }
        
        return switch (tierString.toUpperCase().replace(" ", "_")) {
            case "ARTISAN", "INDIVIDUAL_ARTISAN" -> INDIVIDUAL_ARTISAN;
            case "HOME_MAKER", "HOME_BASED_MAKER" -> HOME_BASED_MAKER;
            case "SMALL_BUSINESS" -> SMALL_BUSINESS;
            case "ENTERPRISE", "ENTERPRISE_SELLER" -> ENTERPRISE_SELLER;
            case "DROPSHIPPER" -> DROPSHIPPER;
            default -> INDIVIDUAL_ARTISAN;
        };
    }
}
