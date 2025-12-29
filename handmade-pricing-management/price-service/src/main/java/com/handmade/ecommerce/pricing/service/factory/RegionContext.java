package com.handmade.ecommerce.pricing.service.factory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Thread-local context for current region and currency
 * Set by interceptor based on request headers or user profile
 * 
 * Configuration:
 * - pricing.default.region: Default region code
 * - pricing.default.currency: Default currency code
 */
@Component
public class RegionContext {

    private static final ThreadLocal<String> currentRegion = new ThreadLocal<>();
    private static final ThreadLocal<String> currentCurrency = new ThreadLocal<>();

    // Configurable defaults from application properties
    private static String defaultRegion;
    private static String defaultCurrency;

    @Value("${pricing.default.region:}")
    public void setDefaultRegion(String region) {
        RegionContext.defaultRegion = region;
    }

    @Value("${pricing.default.currency:}")
    public void setDefaultCurrency(String currency) {
        RegionContext.defaultCurrency = currency;
    }

    /**
     * Set current region for this thread
     * 
     * @param region Region code (e.g., "IN", "US", "EU")
     */
    public static void setRegion(String region) {
        currentRegion.set(region);
    }

    /**
     * Set current currency for this thread
     * 
     * @param currency Currency code (e.g., "INR", "USD", "EUR")
     */
    public static void setCurrency(String currency) {
        currentCurrency.set(currency);
    }

    /**
     * Get current region for this thread
     * 
     * @return Region code, returns configured default or null if not set
     */
    public static String getCurrentRegion() {
        String region = currentRegion.get();
        return region != null ? region : defaultRegion;
    }

    /**
     * Get current currency for this thread
     * 
     * @return Currency code, returns configured default or null if not set
     */
    public static String getCurrentCurrency() {
        String currency = currentCurrency.get();
        return currency != null ? currency : defaultCurrency;
    }

    /**
     * Clear region and currency context (call in finally block)
     */
    public static void clear() {
        currentRegion.remove();
        currentCurrency.remove();
    }
}
