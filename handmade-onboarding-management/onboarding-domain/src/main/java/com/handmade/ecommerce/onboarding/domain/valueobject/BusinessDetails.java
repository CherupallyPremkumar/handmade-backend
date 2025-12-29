package com.handmade.ecommerce.onboarding.domain.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class BusinessDetails {
    @Column(name = "legal_name", length = 255)
    private String legalName;

    @Column(name = "tax_id", length = 50)
    private String taxId;

    @Column(name = "business_type")
    private String businessType;

    // Manual getters and setters for compatibility
    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}
