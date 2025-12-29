package com.handmade.ecommerce.platform.service.impl;

import com.handmade.ecommerce.platform.domain.config.reader.FeatureConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeatureService {
    
    @Autowired
    private FeatureConfigurator featureConfigurator;
    
    public boolean isEnabled(String featureKey) {
        return featureConfigurator.isEnabled(featureKey);
    }
    
    public boolean isDisabled(String featureKey) {
        return !isEnabled(featureKey);
    }
    
    public boolean requireFeature(String featureKey) {
        if (!isEnabled(featureKey)) {
            throw new FeatureNotEnabledException("Feature '" + featureKey + "' is not enabled");
        }
        return true;
    }
    
    public static class FeatureNotEnabledException extends RuntimeException {
        public FeatureNotEnabledException(String message) {
            super(message);
        }
    }
}
