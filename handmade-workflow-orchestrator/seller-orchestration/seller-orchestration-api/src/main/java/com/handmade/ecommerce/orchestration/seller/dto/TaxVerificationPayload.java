package com.handmade.ecommerce.orchestration.seller.dto;

import java.util.Map;

/**
 * Step 5: Tax Verification Payload
 * Input for starting tax verification
 */
public class TaxVerificationPayload {
    
    private String sellerId;
    private String countryCode;
    private String provider; // STRIPE_TAX, GST, VAT
    private Map<String, Object> config;
    
    public TaxVerificationPayload() {
    }
    
    public String getSellerId() {
        return sellerId;
    }
    
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
    
    public String getCountryCode() {
        return countryCode;
    }
    
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    
    public String getProvider() {
        return provider;
    }
    
    public void setProvider(String provider) {
        this.provider = provider;
    }
    
    public Map<String, Object> getConfig() {
        return config;
    }
    
    public void setConfig(Map<String, Object> config) {
        this.config = config;
    }
}
