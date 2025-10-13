package com.homebase.ecom.domain;

import org.chenile.utils.entity.model.BaseEntity;

import java.math.BigDecimal;


public class OrderItem  extends BaseEntity {

    private String orderId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    private String productId;

    private String productName;


    private Integer quantity;

    private BigDecimal price;



    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
