package com.handmade.ecommerce.compliance.domain.config.model;

import com.handmade.ecommerce.compliance.domain.valueobject.ComplianceSeverity;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Compliance policy configuration model
 */
public class CompliancePolicyConfig {
    
    public List<CompliancePolicyDef> policies;
    
    public static class CompliancePolicyDef {
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
        
        // Compliance Configuration
        public Integer minimumComplianceScore;
        public Boolean requiresAnnualReview;
        public Boolean requiresAmlCheck;
        public Boolean requiresDataProtection;
        
        // Rules
        public List<ComplianceRuleDef> rules;
    }
    
    public static class ComplianceRuleDef {
        public String id;
        public String ruleName;
        public Integer ruleOrder;
        public ComplianceSeverity severity;
        public Boolean isRequired;
        public String providerName;
        public Map<String, Object> ruleConfig;
    }
    
    public CompliancePolicyDef getActivePolicy(String countryCode, SellerType sellerType) {
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
    
    public CompliancePolicyDef getPolicyById(String policyId) {
        if (policies == null) {
            return null;
        }
        
        return policies.stream()
            .filter(p -> p.id.equals(policyId))
            .findFirst()
            .orElse(null);
    }
    
    public CompliancePolicyDef getPolicyByVersion(String version) {
        if (policies == null) {
            return null;
        }
        
        return policies.stream()
            .filter(p -> p.version.equals(version))
            .findFirst()
            .orElse(null);
    }
}
