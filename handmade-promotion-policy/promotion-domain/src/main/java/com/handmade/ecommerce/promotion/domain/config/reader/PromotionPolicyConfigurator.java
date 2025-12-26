package com.handmade.ecommerce.promotion.domain.config.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.handmade.ecommerce.promotion.domain.config.model.PromotionPolicyConfig;
import com.handmade.ecommerce.seller.domain.enums.SellerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

/**
 * Promotion policy configurator
 * Loads promotion policies from JSON at startup
 */
@Component
public class PromotionPolicyConfigurator {
    
    private static final Logger logger = LoggerFactory.getLogger(PromotionPolicyConfigurator.class);
    private static final String CONFIG_FILE = "com/handmade/ecommerce/promotion/domain/promotion-policies.json";
    
    public PromotionPolicyConfig promotionPolicyConfig;
    
    @PostConstruct
    public void init() {
        loadConfiguration();
    }
    
    private void loadConfiguration() {
        try {
            logger.info("Loading promotion policy configuration from: {}", CONFIG_FILE);
            
            ClassPathResource resource = new ClassPathResource(CONFIG_FILE);
            InputStream inputStream = resource.getInputStream();
            
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            
            promotionPolicyConfig = objectMapper.readValue(inputStream, PromotionPolicyConfig.class);
            
            logger.info("Successfully loaded {} promotion policies", 
                promotionPolicyConfig.policies.size());
            
            promotionPolicyConfig.policies.forEach(policy -> {
                logger.info("  - Policy: {} ({}) - {} - {}", 
                    policy.version, policy.countryCode, policy.status, policy.promotionName);
            });
            
        } catch (IOException e) {
            logger.error("Failed to load promotion policy configuration", e);
            throw new RuntimeException("Failed to load promotion policy configuration", e);
        }
    }
    
    public PromotionPolicyConfig.PromotionPolicyDef getActivePolicy(String countryCode, SellerType sellerType) {
        return promotionPolicyConfig.getActivePolicy(countryCode, sellerType);
    }
    
    public PromotionPolicyConfig.PromotionPolicyDef getPolicyById(String policyId) {
        return promotionPolicyConfig.getPolicyById(policyId);
    }
    
    public PromotionPolicyConfig.PromotionPolicyDef getPolicyByVersion(String version) {
        return promotionPolicyConfig.getPolicyByVersion(version);
    }
    
    public void reload() {
        loadConfiguration();
    }
}
