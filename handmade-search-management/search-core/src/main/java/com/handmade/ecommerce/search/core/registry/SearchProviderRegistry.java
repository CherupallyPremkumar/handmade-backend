package com.handmade.ecommerce.search.core.registry;

import com.handmade.ecommerce.search.api.SearchProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Registry for search providers.
 * Manages multiple search providers and selects the appropriate one based on entity type.
 */
@Slf4j
@Component
public class SearchProviderRegistry {
    
    private final Map<String, SearchProvider> providers = new HashMap<>();
    private SearchProvider defaultProvider;
    
    /**
     * Auto-wire all available search providers
     */
    @Autowired
    public SearchProviderRegistry(List<SearchProvider> providerList) {
        for (SearchProvider provider : providerList) {
            providers.put(provider.getProviderName(), provider);
            log.info("Registered search provider: {}", provider.getProviderName());
            
            // Set first provider as default
            if (defaultProvider == null) {
                defaultProvider = provider;
                log.info("Set default search provider: {}", provider.getProviderName());
            }
        }
    }
    
    /**
     * Get provider for entity type.
     * Returns the first provider that supports the entity type.
     * Falls back to default provider if none found.
     * 
     * @param entityType Entity type (PRODUCT, SELLER, ORDER)
     * @return Search provider
     */
    public SearchProvider getProvider(String entityType) {
        // Find provider that supports this entity type
        for (SearchProvider provider : providers.values()) {
            if (provider.supports(entityType)) {
                return provider;
            }
        }
        
        // Fall back to default provider
        log.warn("No provider found for entity type: {}, using default provider", entityType);
        return defaultProvider;
    }
    
    /**
     * Get provider by name
     * 
     * @param providerName Provider name
     * @return Search provider or null if not found
     */
    public SearchProvider getProviderByName(String providerName) {
        return providers.get(providerName);
    }
    
    /**
     * Get all registered providers
     * 
     * @return Map of provider name to provider
     */
    public Map<String, SearchProvider> getAllProviders() {
        return new HashMap<>(providers);
    }
}
