package com.handmade.ecommerce.platform.domain.config.model;

import com.handmade.ecommerce.platform.domain.valueobject.SellerTier;
import java.util.List;
import java.util.Map;

public class ComplianceConfig {
    
    public boolean kycRequired;
    public boolean taxIdRequired;
    public int minimumAge;
    public List<String> allowedJurisdictions;
    public List<String> bannedCountries;
    public Map<SellerTier, Boolean> requiresBusinessLicense;
    public int documentExpiryDays;
    
    public boolean isJurisdictionAllowed(String country) {
        if (country == null) {
            return false;
        }
        
        if (bannedCountries != null && bannedCountries.contains(country)) {
            return false;
        }
        
        if (allowedJurisdictions == null || allowedJurisdictions.isEmpty()) {
            return true;
        }
        
        return allowedJurisdictions.contains(country);
    }
    
    public boolean requiresBusinessLicense(SellerTier tier) {
        if (requiresBusinessLicense == null || tier == null) {
            return false;
        }
        return requiresBusinessLicense.getOrDefault(tier, false);
    }
}
