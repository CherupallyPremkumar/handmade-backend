package com.handmade.ecommerce.orchestrator.seller.dto;

/**
 * Step 2: Seller Creation Payload
 * Input for creating seller entity
 */
public class SellerCreationPayload {
    
    private String email;
    private String businessName;
    private String countryCode;
    private String sellerType;
    private String phoneNumber;
    private String website;
    
    public SellerCreationPayload() {
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getBusinessName() {
        return businessName;
    }
    
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    
    public String getCountryCode() {
        return countryCode;
    }
    
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    public String getSellerType() {
        return sellerType;
    }
    
    public void setSellerType(String sellerType) {
        this.sellerType = sellerType;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getWebsite() {
        return website;
    }
    
    public void setWebsite(String website) {
        this.website = website;
    }
}
