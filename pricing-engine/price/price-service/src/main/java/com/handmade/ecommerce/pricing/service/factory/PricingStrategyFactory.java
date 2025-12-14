package com.handmade.ecommerce.pricing.service.factory;

import com.handmade.ecommerce.pricing.service.PricingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Auto-discovery factory for pricing strategies
 * Automatically registers all PricingStrategy implementations
 * 
 * To add a new region:
 * 1. Create a class implementing PricingStrategy
 * 2. Annotate with @Component
 * 3. Implement getRegionCode() to return region code
 * 
 * No code changes needed in this factory!
 */
@Component
public class PricingStrategyFactory {

    private static final Logger logger = LoggerFactory.getLogger(PricingStrategyFactory.class);

    private final Map<String, PricingStrategy> strategies = new ConcurrentHashMap<>();

    @Autowired(required = false)
    private List<PricingStrategy> availableStrategies;

    /**
     * Auto-register all pricing strategies on startup
     */
    @PostConstruct
    public void registerStrategies() {
        if (availableStrategies == null || availableStrategies.isEmpty()) {
            logger.warn("No pricing strategies found! Please implement PricingStrategy interface.");
            return;
        }

        for (PricingStrategy strategy : availableStrategies) {
            String regionCode = strategy.getRegionCode();
            strategies.put(regionCode, strategy);
            logger.info("Registered pricing strategy for region: {}", regionCode);
        }

        logger.info("Total pricing strategies registered: {}", strategies.size());
    }

    /**
     * Get pricing strategy for specific region
     * 
     * @param regionCode Region code (e.g., "IN", "US", "EU")
     * @return Pricing strategy for the region
     * @throws IllegalArgumentException if region not supported
     */
    public PricingStrategy getStrategy(String regionCode) {
        PricingStrategy strategy = strategies.get(regionCode);
        if (strategy == null) {
            logger.error("No pricing strategy found for region: {}. Available regions: {}",
                    regionCode, strategies.keySet());
            throw new IllegalArgumentException("Unsupported region: " + regionCode);
        }
        return strategy;
    }

    /**
     * Get strategy from current context
     * 
     * @return Pricing strategy for current region
     */
    public PricingStrategy getStrategyFromContext() {
        String region = RegionContext.getCurrentRegion();
        return getStrategy(region);
    }

    /**
     * Check if region is supported
     * 
     * @param regionCode Region code to check
     * @return true if supported
     */
    public boolean isRegionSupported(String regionCode) {
        return strategies.containsKey(regionCode);
    }

    /**
     * Get all supported region codes
     * 
     * @return Set of supported region codes
     */
    public java.util.Set<String> getSupportedRegions() {
        return strategies.keySet();
    }
}
