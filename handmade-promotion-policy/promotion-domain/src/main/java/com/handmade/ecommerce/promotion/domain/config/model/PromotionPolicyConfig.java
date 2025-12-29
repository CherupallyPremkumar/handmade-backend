package com.handmade.ecommerce.promotion.domain.config.model;

import com.handmade.ecommerce.promotion.domain.valueobject.DiscountType;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Promotion policy configuration model
 */
public class PromotionPolicyConfig {
    
    public List<PromotionPolicyDef> policies;
    
    public static class PromotionPolicyDef {
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
        
        // Promotion Configuration
        public String promotionName;
        public DiscountType defaultDiscountType;
        public BigDecimal maxDiscountPercentage;
        public BigDecimal maxDiscountAmount;
        public BigDecimal platformCoFundingPercentage;
        public BigDecimal minimumOrderValue;
    }
    
    public PromotionPolicyDef getActivePolicy(String countryCode, SellerType sellerType) {
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
    
    public PromotionPolicyDef getPolicyById(String policyId) {
        if (policies == null) {
            return null;
        }
        
        return policies.stream()
            .filter(p -> p.id.equals(policyId))
            .findFirst()
            .orElse(null);
    }
    
    public PromotionPolicyDef getPolicyByVersion(String version) {
        if (policies == null) {
            return null;
        }
        
        return policies.stream()
            .filter(p -> p.version.equals(version))
            .findFirst()
            .orElse(null);
    }
}
