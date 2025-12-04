package com.handmade.ecommerce.cart.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Request to add item to cart (Amazon-style)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCartItemRequest {
    
    /**
     * Variant ID (specific product variant - size, color, etc.)
     * Similar to Amazon's ASIN
     */
    private String variantId;
    
    /**
     * Seller ID
     */
    private String sellerId;
    
    /**
     * Product name (for display)
     */
    private String productName;
    
    /**
     * Quantity to add
     */
    private Integer quantity;
    
    /**
     * Unit price
     */
    private BigDecimal unitPrice;
    
    /**
     * Currency code
     */
    private String currency;
}
