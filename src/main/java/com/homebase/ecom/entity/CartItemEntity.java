package com.homebase.ecom.entity;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
public class CartItemEntity extends AbstractJpaStateEntity {

    @Column(name = "cart_id")
    private String cartId;

    @Column(name = "prodcut_variant_id", nullable = false)
    private String productVariantId;

    @Column(nullable = false)
    private Integer quantity = 1;

    @Column(precision = 10, scale = 2)
    private BigDecimal snapshotPrice;

    @Column(precision = 10, scale = 2)
    private BigDecimal originalPrice;

    public BigDecimal getSnapshotPrice() {
        return snapshotPrice;
    }

    public void setSnapshotPrice(BigDecimal snapshotPrice) {
        this.snapshotPrice = snapshotPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }



    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

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
