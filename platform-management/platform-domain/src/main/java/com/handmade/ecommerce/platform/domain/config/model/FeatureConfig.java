package com.handmade.ecommerce.platform.domain.config.model;

import java.util.Map;

public class FeatureConfig {
    
    public Map<String, Boolean> features;
    
    public boolean isEnabled(String featureKey) {
        if (features == null || featureKey == null) {
            return false;
        }
        return features.getOrDefault(featureKey, false);
    }
    
    public boolean isDisabled(String featureKey) {
        return !isEnabled(featureKey);
    }
}
