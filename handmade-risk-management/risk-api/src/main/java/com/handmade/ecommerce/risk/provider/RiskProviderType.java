package com.handmade.ecommerce.risk.provider;

/**
 * Risk provider types.
 */
public enum RiskProviderType {
    
    /**
     * Sift fraud detection service.
     * https://sift.com
     */
    SIFT,
    
    /**
     * Riskified fraud detection service.
     * https://www.riskified.com
     */
    RISKIFIED,
    
    /**
     * Stripe Radar (built into Stripe).
     * https://stripe.com/radar
     */
    STRIPE_RADAR,
    
    /**
     * Internal risk engine (custom logic).
     */
    INTERNAL
}
