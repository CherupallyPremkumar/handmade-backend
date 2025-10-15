package com.homebase.ecom.entity;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "hm_cart_item")
public class CartItemEntity extends AbstractJpaStateEntity {

    @Column(name = "cart_id")
    private String cartId;

    @Column(name = "product_variant_id", nullable = false)
    private String productVariantId;
    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 1;

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getProductVariantId() {
        return productVariantId;
    }

    public void setProductVariantId(String productVariantId) {
        this.productVariantId = productVariantId;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
