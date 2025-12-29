package com.handmade.ecommerce.search.api;

import com.handmade.ecommerce.search.api.model.IndexRequest;
import com.handmade.ecommerce.search.api.model.SearchRequest;
import com.handmade.ecommerce.search.api.model.SearchResponse;

/**
 * Search Provider SPI (Service Provider Interface).
 * Implement this interface to create custom search providers (Elasticsearch, Solr, Algolia, etc.)
 */
public interface SearchProvider {
    
    /**
     * Get provider name (e.g., "elasticsearch", "solr")
     * 
     * @return Provider name
     */
    String getProviderName();
    
    /**
     * Check if this provider supports the given entity type
     * 
     * @param entityType Entity type (PRODUCT, SELLER, ORDER)
     * @return true if supported
     */
    boolean supports(String entityType);
    
    /**
     * Execute a search query
     * 
     * @param request Search request
     * @param resultType Result entity class
     * @return Search response
     */
    <T> SearchResponse<T> executeSearch(SearchRequest request, Class<T> resultType);
    
    /**
     * Index a document
     * 
     * @param request Index request
     */
    void indexDocument(IndexRequest request);
    
    /**
     * Index multiple documents in bulk
     * 
     * @param requests List of index requests
     */
    void bulkIndexDocuments(java.util.List<IndexRequest> requests);
    
    /**
     * Delete a document
     * 
     * @param entityType Entity type
     * @param entityId Entity ID
     */
    void deleteDocument(String entityType, String entityId);
    
    /**
     * Create or update index mapping
     * 
     * @param entityType Entity type
     */
    void createIndex(String entityType);
    
    /**
     * Delete index
     * 
     * @param entityType Entity type
     */
    void deleteIndex(String entityType);
}
