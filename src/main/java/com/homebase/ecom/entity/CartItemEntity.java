package com.homebase.ecom.entity;

;

import com.homebase.ecom.entity.ProductEntity;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "cart_items")
public class CartItemEntity extends AbstractJpaStateEntity {

    @Column(name = "cart_id", insertable = false, updatable = false)
    String cartId;

    private String productId;

    private int quantity;
    
    // Price snapshot at the time of adding to cart
    private BigDecimal snapshotPrice;
    
    // Track if the product was on sale when added
    private Boolean wasOnSale = false;
    
    // Original price (non-sale) for reference
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

    public Boolean getWasOnSale() {
        return wasOnSale;
    }

    public void setWasOnSale(Boolean wasOnSale) {
        this.wasOnSale = wasOnSale;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }
}
