package com.handmade.ecommerce.orchestration.seller.dto;

/**
 * Step 1: Policy Resolution Payload
 * Input for resolving onboarding policy
 */
public class PolicyResolutionPayload {
    
    private String countryCode;
    private String sellerType;
    
    public PolicyResolutionPayload() {
    }
    
    public PolicyResolutionPayload(String countryCode, String sellerType) {
        this.countryCode = countryCode;
        this.sellerType = sellerType;
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
}
