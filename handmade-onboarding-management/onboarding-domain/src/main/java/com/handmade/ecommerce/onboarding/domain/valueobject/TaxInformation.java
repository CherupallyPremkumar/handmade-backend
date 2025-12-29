package com.handmade.ecommerce.onboarding.domain.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Tax Information (PII)
 * 
 * SECURITY: Should be encrypted at rest in production
 * Consider using @Convert with encryption converter
 */
@Embeddable
public class TaxInformation {

    @Column(name = "tax_id_type")
    private String taxIdType; // "GST", "VAT", "EIN", "PAN"

    @Column(name = "tax_id_number")
    private String taxIdNumber; // Encrypted in production

    @Column(name = "tax_country")
    private String taxCountry; // ISO 3166-1 alpha-2

    @Column(name = "business_name")
    private String businessName; // Legal business name

    @Column(name = "business_address", length = 500)
    private String businessAddress; // Registered address

    // Constructors
    public TaxInformation() {
    }

    public TaxInformation(String taxIdType, String taxIdNumber, String taxCountry,
            String businessName, String businessAddress) {
        this.taxIdType = taxIdType;
        this.taxIdNumber = taxIdNumber;
        this.taxCountry = taxCountry;
        this.businessName = businessName;
        this.businessAddress = businessAddress;
    }

    // Getters and Setters

    public String getTaxIdType() {
        return taxIdType;
    }

    public void setTaxIdType(String taxIdType) {
        this.taxIdType = taxIdType;
    }

    public String getTaxIdNumber() {
        return taxIdNumber;
    }

    public void setTaxIdNumber(String taxIdNumber) {
        this.taxIdNumber = taxIdNumber;
    }

    public String getTaxCountry() {
        return taxCountry;
    }

    public void setTaxCountry(String taxCountry) {
        this.taxCountry = taxCountry;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }
}
