package com.homebase.admin.entity;

import jakarta.persistence.*;

@Entity
public class TenantConfiguration extends BaseEntity {

    @Column(name = "primary_color", length = 20)
    private String primaryColor;

    @Column(name = "secondary_color", length = 20)
    private String secondaryColor;

    @Column(name = "enable_promotions", nullable = false)
    private Boolean enablePromotions = true;

    @Column(name = "enable_reviews", nullable = false)
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
