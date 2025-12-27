package com.handmade.ecommerce.search.core.service;

import com.handmade.ecommerce.search.api.SearchProvider;
import com.handmade.ecommerce.search.api.SearchService;
import com.handmade.ecommerce.search.api.model.IndexRequest;
import com.handmade.ecommerce.search.api.model.SearchRequest;
import com.handmade.ecommerce.search.api.model.SearchResponse;
import com.handmade.ecommerce.search.core.registry.SearchProviderRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Default implementation of SearchService.
 * Delegates to appropriate search provider based on entity type.
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {
    
    @Autowired
    private SearchProviderRegistry providerRegistry;
    
    @Override
    public <T> SearchResponse<T> search(SearchRequest request, Class<T> resultType) {
        log.debug("Executing search for entity type: {}, query: {}", 
            request.getEntityType(), request.getQuery());
        
        // Validate pagination
        if (request.getPagination() != null) {
            request.getPagination().validate();
        }
        
        // Get appropriate provider
        SearchProvider provider = providerRegistry.getProvider(request.getEntityType());
        
        // Execute search
        long startTime = System.currentTimeMillis();
        SearchResponse<T> response = provider.executeSearch(request, resultType);
        long duration = System.currentTimeMillis() - startTime;
        
        // Set metadata
        if (response.getMetadata() == null) {
            response.setMetadata(new com.handmade.ecommerce.search.api.model.SearchMetadata());
        }
        response.getMetadata().setTook(duration);
        response.getMetadata().setProvider(provider.getProviderName());
        
        log.debug("Search completed in {}ms, found {} results", duration, response.getTotalHits());
        
        return response;
    }
    
    @Override
    public void index(IndexRequest request) {
        log.debug("Indexing document: entityType={}, entityId={}", 
            request.getEntityType(), request.getEntityId());
        
        SearchProvider provider = providerRegistry.getProvider(request.getEntityType());
        provider.indexDocument(request);
    }
    
    @Override
    public void bulkIndex(List<IndexRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            return;
        }
        
        log.debug("Bulk indexing {} documents", requests.size());
        
        // Group by entity type
        Map<String, List<IndexRequest>> groupedRequests = new HashMap<>();
        for (IndexRequest request : requests) {
            groupedRequests.computeIfAbsent(request.getEntityType(), k -> new java.util.ArrayList<>())
                .add(request);
        }
        
        // Index each group with appropriate provider
        for (Map.Entry<String, List<IndexRequest>> entry : groupedRequests.entrySet()) {
            SearchProvider provider = providerRegistry.getProvider(entry.getKey());
            provider.bulkIndexDocuments(entry.getValue());
        }
    }
    
    @Override
    public void delete(String entityType, String entityId) {
        log.debug("Deleting document: entityType={}, entityId={}", entityType, entityId);
        
        SearchProvider provider = providerRegistry.getProvider(entityType);
        provider.deleteDocument(entityType, entityId);
    }
    
    @Override
    public void reindex(String entityType) {
        log.info("Reindexing entity type: {}", entityType);
        
        SearchProvider provider = providerRegistry.getProvider(entityType);
        
        // Delete existing index
        provider.deleteIndex(entityType);
        
        // Create new index
        provider.createIndex(entityType);
        
        // TODO: Trigger bulk indexing from database
        // This will be implemented in Phase 3 with entity-specific indexers
        
        log.info("Reindex completed for entity type: {}", entityType);
    }
    
    @Override
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("providers", providerRegistry.getAllProviders().keySet());
        // TODO: Add more stats (index sizes, document counts, etc.)
        return stats;
    }
}
