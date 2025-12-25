package com.handmade.ecommerce.platform.domain.config.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.handmade.ecommerce.platform.domain.config.model.FeatureConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Component
public class FeatureConfigurator {
    
    private static final Logger logger = LoggerFactory.getLogger(FeatureConfigurator.class);
    private static final String CONFIG_FILE = "com/handmade/ecommerce/platform/feature-flags.json";
    
    public FeatureConfig featureConfig;
    
    @PostConstruct
    public void init() {
        loadConfiguration();
    }
    
    private void loadConfiguration() {
        try {
            logger.info("Loading feature flags from: {}", CONFIG_FILE);
            
            ClassPathResource resource = new ClassPathResource(CONFIG_FILE);
            InputStream inputStream = resource.getInputStream();
            
            ObjectMapper objectMapper = new ObjectMapper();
            featureConfig = objectMapper.readValue(inputStream, FeatureConfig.class);
            
            long enabledCount = featureConfig.features.values().stream()
                .filter(enabled -> enabled)
                .count();
            
            logger.info("Loaded {} feature flags ({} enabled, {} disabled)", 
                featureConfig.features.size(), 
                enabledCount,
                featureConfig.features.size() - enabledCount);
            
        } catch (IOException e) {
            logger.error("Failed to load feature flags", e);
            throw new RuntimeException("Failed to load feature flags", e);
        }
    }
    
    public boolean isEnabled(String featureKey) {
        return featureConfig.isEnabled(featureKey);
    }
    
    public void reload() {
        loadConfiguration();
    }
}
