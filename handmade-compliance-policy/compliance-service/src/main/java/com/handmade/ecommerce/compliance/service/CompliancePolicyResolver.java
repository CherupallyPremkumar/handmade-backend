package com.handmade.ecommerce.compliance.service;

import com.handmade.ecommerce.compliance.domain.aggregate.CompliancePolicy;
import com.handmade.ecommerce.compliance.repository.CompliancePolicyRepository;
import com.handmade.ecommerce.seller.domain.enums.SellerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/**
 * Service for resolving compliance policies
 */
@Service
@Transactional(readOnly = true)
public class CompliancePolicyResolver {
    
    private static final Logger logger = LoggerFactory.getLogger(CompliancePolicyResolver.class);
    
    @Autowired
    private CompliancePolicyRepository policyRepository;
    
    /**
     * Resolve active policy for country and seller type
     */
    public CompliancePolicy resolveActivePolicy(String countryCode, SellerType sellerType) {
        logger.info("Resolving active compliance policy for country={}, sellerType={}", 
            countryCode, sellerType);
        
        LocalDate today = LocalDate.now();
        CompliancePolicy policy = policyRepository.findActivePolicy(countryCode, sellerType, today)
            .orElseThrow(() -> new PolicyNotFoundException(
                String.format("No active compliance policy found for %s %s", 
                    countryCode, sellerType)));
        
        logger.info("Resolved policy: version={}, minScore={}", 
            policy.getVersion(), policy.getMinimumComplianceScore());
        return policy;
    }
    
    /**
     * Get policy by version
     */
    public CompliancePolicy getPolicyByVersion(String version) {
        logger.info("Fetching compliance policy by version={}", version);
        
        return policyRepository.findByVersion(version)
            .orElseThrow(() -> new PolicyNotFoundException(
                "Compliance policy not found for version: " + version));
    }
    
    /**
     * Get all active policies
     */
    public List<CompliancePolicy> getAllActivePolicies() {
        return policyRepository.findAllActive();
    }
    
    /**
     * Get all draft policies
     */
    public List<CompliancePolicy> getAllDraftPolicies() {
        return policyRepository.findAllDrafts();
    }
    
    /**
     * Get all deprecated policies
     */
    public List<CompliancePolicy> getAllDeprecatedPolicies() {
        return policyRepository.findAllDeprecated();
    }
    
    public static class PolicyNotFoundException extends RuntimeException {
        public PolicyNotFoundException(String message) {
            super(message);
        }
    }
}
