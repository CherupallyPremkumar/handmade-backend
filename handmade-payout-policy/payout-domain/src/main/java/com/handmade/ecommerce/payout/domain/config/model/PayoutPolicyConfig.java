package com.handmade.ecommerce.payout.domain.config.model;

import com.handmade.ecommerce.payout.domain.valueobject.PayoutFrequency;
import com.handmade.ecommerce.seller.domain.enums.SellerType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Payout policy configuration model
 * Mirrors OnboardingPolicyConfig pattern from onboarding-policy module
 */
public class PayoutPolicyConfig {
    
    public List<PayoutPolicyDef> policies;
    
    public static class PayoutPolicyDef {
        public String id;
        public String version;
        public String countryCode;
        public SellerType sellerType;
        public String status;
        public LocalDate effectiveFrom;
        public LocalDate effectiveTo;
        public LocalDate deprecatedDate;
        public String createdBy;
        public LocalDateTime createdAt;
        public String approvedBy;
        public LocalDateTime approvedAt;
        public String description;
        public String regulatoryBasis;
        
        // Payout Configuration
        public PayoutFrequency defaultFrequency;
        public BigDecimal minimumThreshold;
        public String currency;
        public Integer holdPeriodDays;
        public BigDecimal rollingReservePercentage;
        public Integer reserveReleaseDays;
        
        // Rules
        public List<PayoutRuleDef> rules;
    }
    
    public static class PayoutRuleDef {
        public String id;
        public String ruleName;
        public Integer ruleOrder;
        public PayoutFrequency frequency;
        public BigDecimal threshold;
        public Integer holdPeriodDays;
        public Map<String, Object> eligibilityCriteria;
        public Map<String, Object> ruleConfig;
    }
    
    /**
     * Get active policy for country and seller type
     */
    public PayoutPolicyDef getActivePolicy(String countryCode, SellerType sellerType) {
        if (policies == null) {
            return null;
        }
        
        LocalDate today = LocalDate.now();
        return policies.stream()
            .filter(p -> "ACTIVE".equals(p.status))
            .filter(p -> p.countryCode.equals(countryCode))
            .filter(p -> p.sellerType == sellerType)
            .filter(p -> p.effectiveFrom == null || !today.isBefore(p.effectiveFrom))
            .filter(p -> p.effectiveTo == null || !today.isAfter(p.effectiveTo))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Get policy by ID
     */
    public PayoutPolicyDef getPolicyById(String policyId) {
        if (policies == null) {
            return null;
        }
        
        return policies.stream()
            .filter(p -> p.id.equals(policyId))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Get policy by version
     */
    public PayoutPolicyDef getPolicyByVersion(String version) {
        if (policies == null) {
            return null;
        }
        
        return policies.stream()
            .filter(p -> p.version.equals(version))
            .findFirst()
            .orElse(null);
    }
}
