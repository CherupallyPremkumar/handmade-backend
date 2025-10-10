package com.homebase.admin.entity;

import java.math.BigDecimal;

public class CartItemPrice implements Priceable {
    private final Product product;
    private final int quantity;

    public CartItemPrice(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public BigDecimal getPrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}