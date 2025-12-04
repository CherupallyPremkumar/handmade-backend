package com.handmade.ecommerce.cartline.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;

/**
 * Cart line item (individual product in cart).
 * 
 * Each line represents one product from one seller.
 * Multiple quantities of same product = single line with quantity > 1
 */
@Getter
@Setter
@Entity
@Table(name = "hm_cartline")
public class Cartline extends AbstractJpaStateEntity {
    
    /**
     * Parent cart
     */
    @Column(name = "cart_id", nullable = false)
    private String cartId;
    
    /**
     * Product ID
     */
    @Column(name = "product_id", nullable = false)
    private String variantId;
    
    /**
     * Seller ID (who sells this product)
     */
    @Column(name = "seller_id", nullable = false)
    private String sellerId;
    
    /**
     * Product name (cached for display)
     */
    @Column(name = "product_name")
    private String productName;
    
    /**
     * Quantity
     */
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    /**
     * Unit price (price per item)
     */
    @Column(name = "unit_price", precision = 19, scale = 4, nullable = false)
    private BigDecimal unitPrice;
    
    /**
     * Total price (quantity × unitPrice)
     */
    @Column(name = "total_price", precision = 19, scale = 4, nullable = false)
    private BigDecimal totalPrice;
    
    /**
     * Currency code
     */
    @Column(name = "currency", length = 3, nullable = false)
    private String currency;
    
    /**
     * Calculate total price
     */
    public void calculateTotalPrice() {
        if (quantity != null && unitPrice != null) {
            this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
        }
    }
}
