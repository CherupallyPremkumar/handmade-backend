package com.handmade.ecommerce.risk.provider;

/**
 * Factory for creating risk providers.
 * 
 * Similar to PaymentExecutorFactory pattern.
 */
public interface RiskProviderFactory {
    
    /**
     * Gets risk provider by type.
     * 
     * @param providerType Provider type (SIFT, RISKIFIED, etc.)
     * @return Risk provider implementation
     */
    RiskProvider getProvider(RiskProviderType providerType);
    
    /**
     * Gets default risk provider from configuration.
     * 
     * @return Default risk provider
     */
    RiskProvider getDefaultProvider();
}
