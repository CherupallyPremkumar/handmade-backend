package com.handmade.ecommerce.promotion.domain.valueobject;

/**
 * Promotion discount types
 */
public enum DiscountType {
    PERCENTAGE("Percentage discount"),
    FIXED_AMOUNT("Fixed amount discount"),
    FREE_SHIPPING("Free shipping"),
    BUY_X_GET_Y("Buy X get Y free");
    
    private final String description;
    
    DiscountType(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
