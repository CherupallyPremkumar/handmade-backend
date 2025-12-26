package com.handmade.ecommerce.orchestrator.seller.dto;

import java.util.Map;

/**
 * Step 6: Bank Verification Payload
 * Input for starting bank verification
 */
public class BankVerificationPayload {
    
    private String sellerId;
    private String countryCode;
    private String provider; // STRIPE_CONNECT, PENNY_DROP, SEPA
    private Map<String, Object> config;
    
    public BankVerificationPayload() {
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
