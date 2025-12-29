package com.handmade.ecommerce.policy.service.config.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.domain.entity.OnboardingPolicyRule;
import com.handmade.ecommerce.policy.repository.OnboardingPolicyRepository;
import com.handmade.ecommerce.policy.repository.OnboardingPolicyRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Loads onboarding policies from JSON file into database
 * 
 * Runs at application startup to seed policy data
 * Follows platform-management pattern for JSON configuration loading
 */
@Component
public class PolicyDataLoader implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(PolicyDataLoader.class);
    
    @Autowired
    private OnboardingPolicyRepository policyRepository;
    
    @Autowired
    private OnboardingPolicyRuleRepository ruleRepository;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        
        logger.info("Loading onboarding policies from JSON...");
        
        // Check if policies already exist
        long existingCount = policyRepository.count();
        if (existingCount > 0) {
            logger.info("Policies already loaded ({} policies exist). Skipping.", existingCount);
            return;
        }
        
        try {
            // Load JSON file
            ClassPathResource resource = new ClassPathResource("com/handmade/ecommerce/policy/domain/onboarding-policies.json");
            InputStream inputStream = resource.getInputStream();
            
            // Parse JSON
            Map<String, Object> data = objectMapper.readValue(inputStream, Map.class);
            List<Map<String, Object>> policies = (List<Map<String, Object>>) data.get("policies");
            
            logger.info("Found {} policies in JSON file", policies.size());
            
            // Load each policy
            for (Map<String, Object> policyData : policies) {
                loadPolicy(policyData);
            }
            
            logger.info("Successfully loaded {} onboarding policies", policies.size());
            
        } catch (IOException e) {
            logger.error("Failed to load onboarding policies from JSON", e);
            throw new RuntimeException("Failed to load onboarding policies", e);
        }
    }
    
    private void loadPolicy(Map<String, Object> policyData) {
        
        // Create policy entity
        OnboardingPolicy policy = new OnboardingPolicy();
        policy.setId((String) policyData.get("id"));
        policy.setPolicyVersion((String) policyData.get("version"));
        policy.setCountryCode((String) policyData.get("countryCode"));
        policy.setSellerType(com.handmade.ecommerce.platform.domain.enums.SellerType.valueOf((String) policyData.get("sellerType")));
        policy.setStatus(com.handmade.ecommerce.policy.domain.valueobject.PolicyStatus.valueOf((String) policyData.get("status")));
        policy.setEffectiveDate(java.time.LocalDate.parse((String) policyData.get("effectiveDate")));
        policy.setCreatedBy((String) policyData.get("createdBy"));
        policy.setCreatedAt(java.time.LocalDateTime.parse((String) policyData.get("createdAt")));
        policy.setApprovedBy((String) policyData.get("approvedBy"));
        policy.setApprovedAt(java.time.LocalDateTime.parse((String) policyData.get("approvedAt")));
        policy.setDescription((String) policyData.get("description"));
        policy.setRegulatoryBasis((String) policyData.get("regulatoryBasis"));
        
        // Save policy
        policyRepository.save(policy);
        
        logger.info("Loaded policy: {} ({})", policy.getPolicyVersion(), policy.getCountryCode());
        
        // Load rules
        List<Map<String, Object>> rules = (List<Map<String, Object>>) policyData.get("rules");
        for (Map<String, Object> ruleData : rules) {
            loadRule(policy.getId(), ruleData);
        }
    }
    
    private void loadRule(String policyId, Map<String, Object> ruleData) {
        
        OnboardingPolicyRule rule = new OnboardingPolicyRule();
        rule.setId((String) ruleData.get("id"));
        rule.setPolicyId(policyId);
        rule.setStepName((String) ruleData.get("stepName"));
        rule.setStepOrder((Integer) ruleData.get("stepOrder"));
        rule.setRequired((Boolean) ruleData.get("required"));
        rule.setProviderType((String) ruleData.get("providerType"));
        
        // Convert provider config to Map
        Map<String, Object> providerConfig = (Map<String, Object>) ruleData.get("providerConfig");
        rule.setProviderConfig(providerConfig);
        
        rule.setMaxRetries((Integer) ruleData.get("maxRetries"));
        rule.setRetryDelayHours((Integer) ruleData.get("retryDelayHours"));
        rule.setMaxDurationDays((Integer) ruleData.get("maxDurationDays"));
        
        // Save rule
        ruleRepository.save(rule);
        
        logger.debug("  - Loaded rule: {} (provider: {}, required: {})", 
                    rule.getStepName(), rule.getProviderType(), rule.isRequired());
    }
}
