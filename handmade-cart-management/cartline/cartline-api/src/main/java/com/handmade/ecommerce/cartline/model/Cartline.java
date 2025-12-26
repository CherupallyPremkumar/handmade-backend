package com.handmade.ecommerce.cartline.model;

import com.handmade.ecommerce.core.model.Region;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import org.chenile.owiz.impl.ChainContext;
import org.chenile.owiz.impl.ChainContextContainer;

import com.handmade.ecommerce.core.model.Money;

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
public class Cartline extends AbstractJpaStateEntity{

    /**
     * Parent cart
     */
    @Column(name = "cart_id", nullable = false)
    private String cartId;

    @Column(name = "region", nullable = false, length = 3)
    private String region;


    @Column(name = "product_id", nullable = false)
    private String variantId;
    

    @Column(name = "seller_id", nullable = false)
    private String sellerId;
    

    @Column(name = "product_name")
    private String productName;
    

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "unit_price_amount")),
        @AttributeOverride(name = "currencyCode", column = @Column(name = "unit_price_currency"))
    })
    private Money unitPrice;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "total_price_amount")),
        @AttributeOverride(name = "currencyCode", column = @Column(name = "total_price_currency"))
    })
    private Money totalPrice;


    @Column(name = "seller_coupon_code")
    private String sellerCouponCode;

    @Column(name = "tax_amount")
    private Money taxAmount;
    

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "amount", column = @Column(name = "seller_discount_amount")),
        @AttributeOverride(name = "currencyCode", column = @Column(name = "seller_discount_currency"))
    })
    private Money sellerDiscount;

    /**
     * Calculate total price: (unitPrice - sellerDiscount) * quantity
     */
    public void calculateTotalPrice() {
        if (unitPrice == null || quantity == null) {
            return;
        }
        
        // Apply seller discount if present
        Money effectivePrice = unitPrice;
        if (sellerDiscount != null) {
            effectivePrice = unitPrice.subtract(sellerDiscount);
        }
        
        this.totalPrice = effectivePrice.multiply(new java.math.BigDecimal(quantity));
    }

    public void incrementQty() {

    }

    public void decrementQty() {
        this.quantity--;
    }
}
