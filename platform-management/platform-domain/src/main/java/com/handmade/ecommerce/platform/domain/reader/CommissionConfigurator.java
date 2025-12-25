package com.handmade.ecommerce.platform.domain.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.handmade.ecommerce.platform.domain.model.CommissionConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Component
public class CommissionConfigurator {
    
    private static final Logger logger = LoggerFactory.getLogger(CommissionConfigurator.class);
    private static final String CONFIG_FILE = "com/handmade/ecommerce/platform/commission-config.json";
    
    public CommissionConfig commissionConfig;
    
    @PostConstruct
    public void init() {
        loadConfiguration();
    }
    
    private void loadConfiguration() {
        try {
            logger.info("Loading commission configuration from: {}", CONFIG_FILE);
            
            ClassPathResource resource = new ClassPathResource(CONFIG_FILE);
            InputStream inputStream = resource.getInputStream();
            
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            
            commissionConfig = objectMapper.readValue(inputStream, CommissionConfig.class);
            
            logger.info("Successfully loaded {} commission policies", 
                commissionConfig.commissionPolicies.size());
            
            CommissionConfig.PolicyConfig activePolicy = commissionConfig.getActivePolicy();
            if (activePolicy != null) {
                logger.info("Active commission policy: {} ({})", 
                    activePolicy.policyName, activePolicy.policyId);
            } else {
                logger.warn("No active commission policy found!");
            }
            
        } catch (IOException e) {
            logger.error("Failed to load commission configuration", e);
            throw new RuntimeException("Failed to load commission configuration", e);
        }
    }
    
    public CommissionConfig.PolicyConfig getActivePolicy() {
        return commissionConfig.getActivePolicy();
    }
    
    public CommissionConfig.PolicyConfig getPolicyById(String policyId) {
        return commissionConfig.getPolicyById(policyId);
    }
    
    public void reload() {
        loadConfiguration();
    }
}
