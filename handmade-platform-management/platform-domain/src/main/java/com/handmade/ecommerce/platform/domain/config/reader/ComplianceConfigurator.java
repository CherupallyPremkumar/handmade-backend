package com.handmade.ecommerce.platform.domain.config.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.ecommerce.platform.domain.config.model.ComplianceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Component
public class ComplianceConfigurator {
    
    private static final Logger logger = LoggerFactory.getLogger(ComplianceConfigurator.class);
    private static final String CONFIG_FILE = "com/handmade/ecommerce/platform/compliance-rules.json";
    
    public ComplianceConfig complianceConfig;
    
    @PostConstruct
    public void init() {
        loadConfiguration();
    }
    
    private void loadConfiguration() {
        try {
            logger.info("Loading compliance rules from: {}", CONFIG_FILE);
            
            ClassPathResource resource = new ClassPathResource(CONFIG_FILE);
            InputStream inputStream = resource.getInputStream();
            
            ObjectMapper objectMapper = new ObjectMapper();
            complianceConfig = objectMapper.readValue(inputStream, ComplianceConfig.class);
            
            logger.info("Loaded compliance rules: KYC={}, TaxID={}, AllowedJurisdictions={}", 
                complianceConfig.kycRequired,
                complianceConfig.taxIdRequired,
                complianceConfig.allowedJurisdictions.size());
            
        } catch (IOException e) {
            logger.error("Failed to load compliance rules", e);
            throw new RuntimeException("Failed to load compliance rules", e);
        }
    }
    
    public void reload() {
        loadConfiguration();
    }
}
