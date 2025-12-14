package com.handmade.ecommerce.payment.service.factory;

import com.handmade.ecommerce.payment.service.provider.BankVerificationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Factory to create/retrieve bank verification providers
 * Centralizes the logic for selecting the correct provider based on context
 */
@Component
public class BankVerificationProviderFactory {

    private final Map<String, BankVerificationProvider> providers;

    @Autowired
    public BankVerificationProviderFactory(List<BankVerificationProvider> providerList) {
        this.providers = new HashMap<>();
        for (BankVerificationProvider provider : providerList) {
            providers.put(provider.getProviderName().toUpperCase(), provider);
        }
    }

    /**
     * Get provider by name
     * 
     * @param providerName Provider name (RAZORPAY, STRIPE)
     * @return Provider implementation
     * @throws IllegalArgumentException if provider not found
     */
    public BankVerificationProvider getProvider(String providerName) {
        BankVerificationProvider provider = providers.get(providerName.toUpperCase());
        if (provider == null) {
            throw new IllegalArgumentException("No verification provider found for name: " + providerName);
        }
        return provider;
    }

    /**
     * Get provider for a specific country
     * Returns the first provider that supports the country
     * 
     * @param countryCode ISO 2-letter country code
     * @return Provider implementation
     * @throws IllegalArgumentException if no provider supports the country
     */
    public BankVerificationProvider getProviderForCountry(String countryCode) {
        for (BankVerificationProvider provider : providers.values()) {
            if (provider.supportsCountry(countryCode)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("No verification provider found for country: " + countryCode);
    }

    /**
     * Check if a provider is registered
     * 
     * @param providerName Provider name
     * @return true if registered, false otherwise
     */
    public boolean hasProvider(String providerName) {
        return providers.containsKey(providerName.toUpperCase());
    }

    /**
     * Get all registered provider names
     * 
     * @return Set of provider names
     */
    public java.util.Set<String> getRegisteredProviders() {
        return providers.keySet();
    }
}
