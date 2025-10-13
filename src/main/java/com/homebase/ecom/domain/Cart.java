package com.homebase.ecom.domain;

import org.chenile.utils.entity.model.AbstractExtendedStateEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Cart extends AbstractExtendedStateEntity {

    private List<CartItem> cartItems = new ArrayList<>();
    
    private int quantity;
    private BigDecimal total = BigDecimal.ZERO;
    private BigDecimal salePrice = BigDecimal.ZERO;
    
    private CartStatus status = CartStatus.OPEN;

    private Customer customer;

    public enum CartStatus {
        OPEN, CHECKED_OUT, ABANDONED
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CartStatus getStatus() {
        return status;
    }

    public void setStatus(CartStatus status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }



    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }


    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

}

