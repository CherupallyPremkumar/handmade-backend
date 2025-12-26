package com.handmade.ecommerce.payout.domain.config.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.handmade.ecommerce.payout.domain.config.model.PayoutPolicyConfig;
import com.handmade.ecommerce.seller.domain.enums.SellerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * Payout policy configurator
 * Mirrors PolicyConfigurator pattern from onboarding-policy module
 * Loads payout policies from JSON at startup
 */
@Component
public class PayoutPolicyConfigurator {
    
    private static final Logger logger = LoggerFactory.getLogger(PayoutPolicyConfigurator.class);
    private static final String CONFIG_FILE = "com/handmade/ecommerce/payout/domain/payout-policies.json";
    
    public PayoutPolicyConfig payoutPolicyConfig;
    
    @PostConstruct
    public void init() {
        loadConfiguration();
    }
    
    private void loadConfiguration() {
        try {
            logger.info("Loading payout policy configuration from: {}", CONFIG_FILE);
            
            ClassPathResource resource = new ClassPathResource(CONFIG_FILE);
            InputStream inputStream = resource.getInputStream();
            
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            
            payoutPolicyConfig = objectMapper.readValue(inputStream, PayoutPolicyConfig.class);
            
            logger.info("Successfully loaded {} payout policies", 
                payoutPolicyConfig.policies.size());
            
            // Log loaded policies
            payoutPolicyConfig.policies.forEach(policy -> {
                logger.info("  - Policy: {} ({}) - {} - {} rules", 
                    policy.version, policy.countryCode, policy.status, 
                    policy.rules != null ? policy.rules.size() : 0);
            });
            
        } catch (IOException e) {
            logger.error("Failed to load payout policy configuration", e);
            throw new RuntimeException("Failed to load payout policy configuration", e);
        }
    }
    
    /**
     * Get active policy for country and seller type
     */
    public PayoutPolicyConfig.PayoutPolicyDef getActivePolicy(String countryCode, SellerType sellerType) {
        return payoutPolicyConfig.getActivePolicy(countryCode, sellerType);
    }
    
    /**
     * Get policy by ID
     */
    public PayoutPolicyConfig.PayoutPolicyDef getPolicyById(String policyId) {
        return payoutPolicyConfig.getPolicyById(policyId);
    }
    
    /**
     * Get policy by version
     */
    public PayoutPolicyConfig.PayoutPolicyDef getPolicyByVersion(String version) {
        return payoutPolicyConfig.getPolicyByVersion(version);
    }
    
    /**
     * Reload configuration from file
     */
    public void reload() {
        loadConfiguration();
    }
}
