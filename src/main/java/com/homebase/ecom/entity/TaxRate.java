package com.homebase.ecom.entity;

import com.homebase.ecom.domain.MultiTenantBaseEntity;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.util.Date;

@Entity
public class TaxRate extends MultiTenantEntity {
    private String country;
        private String state;
        private String productCategory;
        private BigDecimal rate; // e.g., 0.07 for 7%
        private Date effectiveFrom;
        private Date effectiveTo;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
    }
}

