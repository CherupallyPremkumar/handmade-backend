package com.handmade.ecommerce.commission.domain.config.model;

import com.handmade.ecommerce.seller.domain.enums.SellerType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class CommissionConfig {
    
    public List<PolicyConfig> commissionPolicies;
    
    public static class PolicyConfig {
        public String policyId;
        public String policyName;
        public boolean isActive;
        public LocalDateTime effectiveFrom;
        public LocalDateTime effectiveTo;
        public BigDecimal baseCommissionRate;
        public BigDecimal processingFeeRate;
        public Map<SellerType, BigDecimal> tierRates;
        public List<VolumeDiscount> volumeDiscounts;
    }
    
    public static class VolumeDiscount {
        public BigDecimal threshold;
        public BigDecimal discountRate;
        public String description;
    }
    
    public PolicyConfig getActivePolicy() {
        if (commissionPolicies == null) {
            return null;
        }
        
        LocalDateTime now = LocalDateTime.now();
        return commissionPolicies.stream()
            .filter(p -> p.isActive)
            .filter(p -> p.effectiveFrom == null || !now.isBefore(p.effectiveFrom))
            .filter(p -> p.effectiveTo == null || !now.isAfter(p.effectiveTo))
            .findFirst()
            .orElse(null);
    }
    
    public PolicyConfig getPolicyById(String policyId) {
        if (commissionPolicies == null) {
            return null;
        }
        
        return commissionPolicies.stream()
            .filter(p -> p.policyId.equals(policyId))
            .findFirst()
            .orElse(null);
    }
}
