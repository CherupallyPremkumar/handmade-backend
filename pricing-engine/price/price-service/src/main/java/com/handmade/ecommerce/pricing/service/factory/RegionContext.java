package com.handmade.ecommerce.pricing.service.factory;

/**
 * Thread-local context for current region
 * Set by interceptor based on request headers or user profile
 */
public class RegionContext {

    private static final ThreadLocal<String> currentRegion = new ThreadLocal<>();
    private static final String DEFAULT_REGION = "IN"; // India as default

    /**
     * Set current region for this thread
     * 
     * @param region Region code (e.g., "IN", "US", "EU")
     */
    public static void setRegion(String region) {
        currentRegion.set(region);
    }

    /**
     * Get current region for this thread
     * 
     * @return Region code, defaults to "IN" if not set
     */
    public static String getCurrentRegion() {
        String region = currentRegion.get();
        return region != null ? region : DEFAULT_REGION;
    }

    /**
     * Clear region context (call in finally block)
     */
    public static void clear() {
        currentRegion.remove();
    }
}
