package com.handmade.ecommerce.platform.domain.config.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.ecommerce.platform.domain.config.model.OperationalLimitsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Component
public class OperationalLimitsConfigurator {
    
    private static final Logger logger = LoggerFactory.getLogger(OperationalLimitsConfigurator.class);
    private static final String CONFIG_FILE = "com/handmade/ecommerce/platform/operational-limits.json";
    
    public OperationalLimitsConfig operationalLimitsConfig;
    
    @PostConstruct
    public void init() {
        loadConfiguration();
    }
    
    private void loadConfiguration() {
        try {
            logger.info("Loading operational limits from: {}", CONFIG_FILE);
            
            ClassPathResource resource = new ClassPathResource(CONFIG_FILE);
            InputStream inputStream = resource.getInputStream();
            
            ObjectMapper objectMapper = new ObjectMapper();
            operationalLimitsConfig = objectMapper.readValue(inputStream, OperationalLimitsConfig.class);
            
            logger.info("Loaded operational limits: MaxSellers/Day={}, MaxTransaction={}, RateLimit={}", 
                operationalLimitsConfig.maxSellersPerDay,
                operationalLimitsConfig.maxTransactionAmount,
                operationalLimitsConfig.globalRateLimit);
            
        } catch (IOException e) {
            logger.error("Failed to load operational limits", e);
            throw new RuntimeException("Failed to load operational limits", e);
        }
    }
    
    public void reload() {
        loadConfiguration();
    }
}
