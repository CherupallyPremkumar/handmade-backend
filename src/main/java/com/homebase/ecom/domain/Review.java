package com.homebase.ecom.domain;

import org.chenile.utils.entity.model.BaseEntity;

public class Review extends MultiTenantBaseEntity {

    public void setProductVariantId(String productVariantId) {
        this.productVariantId = productVariantId;
    }

    private String productVariantId;
    private String customerId;
    private int rating;
    private String comment;

    public String getProductVariantId() {
        return productVariantId;
    }




    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
