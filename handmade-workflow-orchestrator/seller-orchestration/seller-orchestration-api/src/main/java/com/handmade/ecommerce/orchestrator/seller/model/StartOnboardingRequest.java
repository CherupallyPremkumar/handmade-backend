package com.handmade.ecommerce.orchestrator.seller.model;

/**
 * Request DTO to start seller onboarding workflow
 */
public class StartOnboardingRequest {
    
    private String email;
    private String businessName;
    private String countryCode;
    private String sellerType;
    
    // Additional optional fields
    private String phoneNumber;
    private String website;
    private String businessAddress;
    
    public StartOnboardingRequest() {
    }
    
    public StartOnboardingRequest(String email, String businessName, String countryCode, String sellerType) {
        this.email = email;
        this.businessName = businessName;
        this.countryCode = countryCode;
        this.sellerType = sellerType;
    }
    
    // Getters and Setters
    
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
    
    public String getBusinessAddress() {
        return businessAddress;
    }
    
    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }
}
