package com.handmade.ecommerce.compliance.domain.config.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.handmade.ecommerce.compliance.domain.config.model.CompliancePolicyConfig;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * Compliance policy configurator
 * Loads compliance policies from JSON at startup
 */
@Component
public class CompliancePolicyConfigurator {
    
    private static final Logger logger = LoggerFactory.getLogger(CompliancePolicyConfigurator.class);
    private static final String CONFIG_FILE = "com/handmade/ecommerce/compliance/domain/compliance-policies.json";
    
    public CompliancePolicyConfig compliancePolicyConfig;
    
    @PostConstruct
    public void init() {
        loadConfiguration();
    }
    
    private void loadConfiguration() {
        try {
            logger.info("Loading compliance policy configuration from: {}", CONFIG_FILE);
            
            ClassPathResource resource = new ClassPathResource(CONFIG_FILE);
            InputStream inputStream = resource.getInputStream();
            
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            
            compliancePolicyConfig = objectMapper.readValue(inputStream, CompliancePolicyConfig.class);
            
            logger.info("Successfully loaded {} compliance policies", 
                compliancePolicyConfig.policies.size());
            
            compliancePolicyConfig.policies.forEach(policy -> {
                logger.info("  - Policy: {} ({}) - {} - {} rules", 
                    policy.version, policy.countryCode, policy.status, 
                    policy.rules != null ? policy.rules.size() : 0);
            });
            
        } catch (IOException e) {
            logger.error("Failed to load compliance policy configuration", e);
            throw new RuntimeException("Failed to load compliance policy configuration", e);
        }
    }
    
    public CompliancePolicyConfig.CompliancePolicyDef getActivePolicy(String countryCode, SellerType sellerType) {
        return compliancePolicyConfig.getActivePolicy(countryCode, sellerType);
    }
    
    public CompliancePolicyConfig.CompliancePolicyDef getPolicyById(String policyId) {
        return compliancePolicyConfig.getPolicyById(policyId);
    }
    
    public CompliancePolicyConfig.CompliancePolicyDef getPolicyByVersion(String version) {
        return compliancePolicyConfig.getPolicyByVersion(version);
    }
    
    public void reload() {
        loadConfiguration();
    }
}
