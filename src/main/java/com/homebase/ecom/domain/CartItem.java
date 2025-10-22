package com.homebase.ecom.domain;


import org.chenile.utils.entity.model.AbstractExtendedStateEntity;
import org.springframework.context.event.EventListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartItem extends MultiTenantExtendedStateEntity {

    private String cartId;

    private String productVariantId;

    private Integer quantity;

    private BigDecimal snapshotPrice;

    private BigDecimal salePrice;

    private BigDecimal originalPrice;

    private BigDecimal taxRate;

    private BigDecimal taxAmount;

    private BigDecimal totalAmount = BigDecimal.ZERO;

    private boolean wasOnSale;

    private String country;
    private String state;
    private String productCategory;


    @EventListener
    public boolean isOutOfQuanity(){
       return this.quantity==0;
    }

    // Increment quantity safely
    public void incrementQuantity(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Increment amount must be positive");
        }
        this.quantity += amount;
    }

    public void decrementQuantity(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Decrement must be positive");
        this.quantity = Math.max(0, this.quantity - amount);
    }


    public boolean isOnSale() {
        return salePrice != null && salePrice.compareTo(originalPrice) < 0;
    }

    public BigDecimal getSnapshotPrice() {
        return snapshotPrice;
    }

    public void setSnapshotPrice(BigDecimal snapshotPrice) {
        this.snapshotPrice = snapshotPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
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

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }


    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isWasOnSale() {
        return wasOnSale;
    }

    public void setWasOnSale(boolean wasOnSale) {
        this.wasOnSale = wasOnSale;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }
}
