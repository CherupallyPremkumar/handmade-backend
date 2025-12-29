package com.handmade.ecommerce.policy.domain.config.model;

import com.handmade.ecommerce.platform.domain.enums.SellerType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Policy configuration model
 * Mirrors CommissionConfig pattern from platform-domain
 */
public class PolicyConfig {
    
    public List<OnboardingPolicyDef> policies;
    
    public static class OnboardingPolicyDef {
        public String id;
        public String version;
        public String countryCode;
        public SellerType sellerType;
        public String status;
        public LocalDate effectiveDate;
        public LocalDate deprecatedDate;
        public String createdBy;
        public LocalDateTime createdAt;
        public String approvedBy;
        public LocalDateTime approvedAt;
        public String description;
        public String regulatoryBasis;
        public List<PolicyRuleDef> rules;
    }
    
    public static class PolicyRuleDef {
        public String id;
        public String stepName;
        public int stepOrder;
        public boolean required;
        public String providerType;
        public Map<String, Object> providerConfig;
        public int maxRetries;
        public int retryDelayHours;
        public int maxDurationDays;
    }
    
    /**
     * Get active policy for country and seller type
     */
    public OnboardingPolicyDef getActivePolicy(String countryCode, SellerType sellerType) {
        if (policies == null) {
            return null;
        }
        
        LocalDate today = LocalDate.now();
        return policies.stream()
            .filter(p -> "ACTIVE".equals(p.status))
            .filter(p -> p.countryCode.equals(countryCode))
            .filter(p -> p.sellerType == sellerType)
            .filter(p -> p.effectiveDate == null || !today.isBefore(p.effectiveDate))
            .filter(p -> p.deprecatedDate == null || !today.isAfter(p.deprecatedDate))
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Get policy by ID
     */
    public OnboardingPolicyDef getPolicyById(String policyId) {
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
    public OnboardingPolicyDef getPolicyByVersion(String version) {
        if (policies == null) {
            return null;
        }
        
        return policies.stream()
            .filter(p -> p.version.equals(version))
            .findFirst()
            .orElse(null);
    }
}
