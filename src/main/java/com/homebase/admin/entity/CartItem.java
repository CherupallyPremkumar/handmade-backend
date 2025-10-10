package com.homebase.admin.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cart_items")
public class CartItem extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    
    // Price snapshot at the time of adding to cart
    private BigDecimal snapshotPrice;
    
    // Track if the product was on sale when added
    private Boolean wasOnSale = false;
    
    // Original price (non-sale) for reference
    private BigDecimal originalPrice;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

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

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
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
