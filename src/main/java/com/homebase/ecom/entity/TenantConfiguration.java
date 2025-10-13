package com.homebase.ecom.entity;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Table(name = "tenant_configuration")
public class TenantConfiguration extends BaseJpaEntity {

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "primary_color", length = 20)
    private String primaryColor;

    @Column(name = "secondary_color", length = 20)
    private String secondaryColor;

    @Column(name = "enable_promotions", nullable = false)
    private Boolean enablePromotions = true;

    @Column(name = "enable_reviews", nullable = false)
    private Boolean enableReviews = true;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

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
