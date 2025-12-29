package com.handmade.ecommerce.policy.domain.config.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.handmade.ecommerce.policy.domain.config.model.PolicyConfig;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * Policy configurator
 * Mirrors CommissionConfigurator pattern from platform-domain
 */
@Component
public class PolicyConfigurator {
    
    private static final Logger logger = LoggerFactory.getLogger(PolicyConfigurator.class);
    private static final String CONFIG_FILE = "com/handmade/ecommerce/policy/domain/onboarding-policies.json";
    
    public PolicyConfig policyConfig;
    
    @PostConstruct
    public void init() {
        loadConfiguration();
    }
    
    private void loadConfiguration() {
        try {
            logger.info("Loading onboarding policy configuration from: {}", CONFIG_FILE);
            
            ClassPathResource resource = new ClassPathResource(CONFIG_FILE);
            InputStream inputStream = resource.getInputStream();
            
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            
            policyConfig = objectMapper.readValue(inputStream, PolicyConfig.class);
            
            logger.info("Successfully loaded {} onboarding policies", 
                policyConfig.policies.size());
            
            // Log loaded policies
            policyConfig.policies.forEach(policy -> {
                logger.info("  - Policy: {} ({}) - {} - {} rules", 
                    policy.version, policy.countryCode, policy.status, 
                    policy.rules != null ? policy.rules.size() : 0);
            });
            
        } catch (IOException e) {
            logger.error("Failed to load onboarding policy configuration", e);
            throw new RuntimeException("Failed to load onboarding policy configuration", e);
        }
    }
    
    /**
     * Get active policy for country and seller type
     */
    public PolicyConfig.OnboardingPolicyDef getActivePolicy(String countryCode, SellerType sellerType) {
        return policyConfig.getActivePolicy(countryCode, sellerType);
    }
    
    /**
     * Get policy by ID
     */
    public PolicyConfig.OnboardingPolicyDef getPolicyById(String policyId) {
        return policyConfig.getPolicyById(policyId);
    }
    
    /**
     * Get policy by version
     */
    public PolicyConfig.OnboardingPolicyDef getPolicyByVersion(String version) {
        return policyConfig.getPolicyByVersion(version);
    }
    
    /**
     * Reload configuration from file
     */
    public void reload() {
        loadConfiguration();
    }
}
