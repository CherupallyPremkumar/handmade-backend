package com.handmade.ecommerce.promotion.service;

import com.handmade.ecommerce.promotion.domain.aggregate.PromotionPolicy;
import com.handmade.ecommerce.promotion.repository.PromotionPolicyRepository;
import com.handmade.ecommerce.seller.domain.enums.SellerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service for resolving promotion policies
 */
@Service
@Transactional(readOnly = true)
public class PromotionPolicyResolver {
    
    private static final Logger logger = LoggerFactory.getLogger(PromotionPolicyResolver.class);
    
    @Autowired
    private PromotionPolicyRepository policyRepository;
    
    /**
     * Resolve active policy for country and seller type
     */
    public PromotionPolicy resolveActivePolicy(String countryCode, SellerType sellerType) {
        logger.info("Resolving active promotion policy for country={}, sellerType={}", 
            countryCode, sellerType);
        
        LocalDate today = LocalDate.now();
        PromotionPolicy policy = policyRepository.findActivePolicy(countryCode, sellerType, today)
            .orElseThrow(() -> new PolicyNotFoundException(
                String.format("No active promotion policy found for %s %s", 
                    countryCode, sellerType)));
        
        logger.info("Resolved policy: version={}, promotion={}", 
            policy.getVersion(), policy.getPromotionName());
        return policy;
    }
    
    /**
     * Get policy by version
     */
    public PromotionPolicy getPolicyByVersion(String version) {
        logger.info("Fetching promotion policy by version={}", version);
        
        return policyRepository.findByVersion(version)
            .orElseThrow(() -> new PolicyNotFoundException(
                "Promotion policy not found for version: " + version));
    }
    
    /**
     * Get all currently active promotions
     */
    public List<PromotionPolicy> getAllActivePromotionsToday() {
        LocalDate today = LocalDate.now();
        return policyRepository.findAllActiveToday(today);
    }
    
    /**
     * Get all active policies
     */
    public List<PromotionPolicy> getAllActivePolicies() {
        return policyRepository.findAllActive();
    }
    
    /**
     * Get all draft policies
     */
    public List<PromotionPolicy> getAllDraftPolicies() {
        return policyRepository.findAllDrafts();
    }
    
    /**
     * Get all deprecated policies
     */
    public List<PromotionPolicy> getAllDeprecatedPolicies() {
        return policyRepository.findAllDeprecated();
    }
    
    public static class PolicyNotFoundException extends RuntimeException {
        public PolicyNotFoundException(String message) {
            super(message);
        }
    }
}
