package com.homebase.ecom.domain;

import java.math.BigDecimal;

public class CartItemPrice {
    private final Product product;
    private final int quantity;

    public CartItemPrice(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }


    public BigDecimal getPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}