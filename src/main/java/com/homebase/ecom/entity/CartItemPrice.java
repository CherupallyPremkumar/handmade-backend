package com.homebase.ecom.entity;

import com.homebase.ecom.entity.ProductEntity;

import java.math.BigDecimal;

public class CartItemPrice  {
    private final ProductEntity product;
    private final int quantity;

    public CartItemPrice(ProductEntity product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }


    public BigDecimal getPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}