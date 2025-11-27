package com.handmade.ecommerce.pricing.model;

/**
 * Tax Type Enum
 * Represents different types of taxes
 */
public enum TaxType {
    /**
     * Value Added Tax (Europe)
     */
    VAT,
    
    /**
     * Goods and Services Tax (India, Australia, etc.)
     */
    GST,
    
    /**
     * Sales Tax (US)
     */
    SALES_TAX,
    
    /**
     * Import Duty
     */
    IMPORT_DUTY,
    
    /**
     * No tax
     */
    NONE
}
