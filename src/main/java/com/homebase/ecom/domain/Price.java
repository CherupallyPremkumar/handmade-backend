package com.homebase.ecom.domain;

import org.chenile.utils.entity.model.AbstractExtendedStateEntity;


public class Price extends AbstractExtendedStateEntity {

    private String productId;
    private String currency;
    private String region;
    private Double amount;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
