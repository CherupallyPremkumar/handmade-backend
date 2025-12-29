package com.handmade.ecommerce.seller.domain.valueobject;

import com.handmade.ecommerce.platform.domain.enums.SellerType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;

/**
 * Business registration and verification details
 */
@Embeddable
public class BusinessDetails implements Serializable {
    
    @Enumerated(EnumType.STRING)
    private SellerType sellerType;
    
    private String legalBusinessName;
    private String taxId;
    private String businessLicenseNumber;
    private String businessAddressLine1;
    private String businessAddressLine2;
    private String businessCity;
    private String businessState;
    private String businessZip;
    private String businessCountry;
    private String phoneNumber;

    public BusinessDetails() {
    }

    public BusinessDetails(SellerType sellerType, String legalBusinessName, String taxId) {
        this.sellerType = sellerType;
        this.legalBusinessName = legalBusinessName;
        this.taxId = taxId;
    }

    // Getters and Setters
    public SellerType getSellerType() {
        return sellerType;
    }

    public void setSellerType(SellerType sellerType) {
        this.sellerType = sellerType;
    }

    public String getLegalBusinessName() {
        return legalBusinessName;
    }

    public void setLegalBusinessName(String legalBusinessName) {
        this.legalBusinessName = legalBusinessName;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getBusinessLicenseNumber() {
        return businessLicenseNumber;
    }

    public void setBusinessLicenseNumber(String businessLicenseNumber) {
        this.businessLicenseNumber = businessLicenseNumber;
    }

    public String getBusinessAddressLine1() {
        return businessAddressLine1;
    }

    public void setBusinessAddressLine1(String businessAddressLine1) {
        this.businessAddressLine1 = businessAddressLine1;
    }

    public String getBusinessAddressLine2() {
        return businessAddressLine2;
    }

    public void setBusinessAddressLine2(String businessAddressLine2) {
        this.businessAddressLine2 = businessAddressLine2;
    }

    public String getBusinessCity() {
        return businessCity;
    }

    public void setBusinessCity(String businessCity) {
        this.businessCity = businessCity;
    }

    public String getBusinessState() {
        return businessState;
    }

    public void setBusinessState(String businessState) {
        this.businessState = businessState;
    }

    public String getBusinessZip() {
        return businessZip;
    }

    public void setBusinessZip(String businessZip) {
        this.businessZip = businessZip;
    }

    public String getBusinessCountry() {
        return businessCountry;
    }

    public void setBusinessCountry(String businessCountry) {
        this.businessCountry = businessCountry;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
