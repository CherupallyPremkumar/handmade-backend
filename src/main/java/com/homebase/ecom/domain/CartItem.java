package com.homebase.ecom.domain;

import org.chenile.utils.entity.model.AbstractExtendedStateEntity;

import java.math.BigDecimal;

public class CartItem extends MultiTenantExtendedStateEntity {

    private Cart cart;

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
