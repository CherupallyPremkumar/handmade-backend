package com.handmade.ecommerce.search.core;

import org.springframework.stereotype.Service;
import java.util.*;

/**
 * SearchProviderRegistry - Auto-discovers and manages search provider plugins
 */
@Service
public class SearchProviderRegistry {
    
    private final Map<String, SearchProvider> providers;
    private final SearchProvider activeProvider;
    
    public SearchProviderRegistry(List<SearchProvider> searchProviders) {
        this.providers = new HashMap<>();
        SearchProvider active = null;
        
        for (SearchProvider provider : searchProviders) {
            if (provider.isEnabled()) {
                providers.put(provider.getProviderCode(), provider);
                if (active == null) {
                    active = provider; // First enabled provider is active
                }
                System.out.println(String.format(
                    "✅ Registered search provider: %s (%s)",
                    provider.getProviderCode(),
                    provider.getProviderName()
                ));
            }
        }
        
        this.activeProvider = active;
        
        if (activeProvider != null) {
            System.out.println("🔍 Active search provider: " + activeProvider.getProviderCode());
        } else {
            System.out.println("⚠️  No search provider enabled!");
        }
    }
    
    /**
     * Get active search provider
     */
    public SearchProvider getActiveProvider() {
        if (activeProvider == null) {
            throw new NoSearchProviderException("No search provider is enabled");
        }
        return activeProvider;
    }
    
    /**
     * Get provider by code
     */
    public SearchProvider getProvider(String providerCode) {
        SearchProvider provider = providers.get(providerCode);
        if (provider == null) {
            throw new ProviderNotFoundException(
                "Search provider not found: " + providerCode
            );
        }
        return provider;
    }
    
    /**
     * Get all providers
     */
    public Collection<SearchProvider> getAllProviders() {
        return providers.values();
    }
    
    /**
     * Get available provider codes
     */
    public List<String> getAvailableProviders() {
        return new ArrayList<>(providers.keySet());
    }
    
    public static class NoSearchProviderException extends RuntimeException {
        public NoSearchProviderException(String message) {
            super(message);
        }
    }
    
    public static class ProviderNotFoundException extends RuntimeException {
        public ProviderNotFoundException(String message) {
            super(message);
        }
    }
}
