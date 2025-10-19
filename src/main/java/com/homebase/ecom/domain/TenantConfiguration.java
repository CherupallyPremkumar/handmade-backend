package com.homebase.ecom.domain;

import org.chenile.utils.entity.model.AbstractExtendedStateEntity;

public class TenantConfiguration extends AbstractExtendedStateEntity {


    private String primaryColor;
    private String secondaryColor;
    private Boolean enablePromotions = true;
    private Boolean enableReviews = true;


    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSecondaryColor() {
        return secondaryColor;
    }

    public void setSecondaryColor(String secondaryColor) {
        this.secondaryColor = secondaryColor;
    }

    public Boolean getEnablePromotions() {
        return enablePromotions;
    }

    public void setEnablePromotions(Boolean enablePromotions) {
        this.enablePromotions = enablePromotions;
    }

    public Boolean getEnableReviews() {
        return enableReviews;
    }

    public void setEnableReviews(Boolean enableReviews) {
        this.enableReviews = enableReviews;
    }
}
