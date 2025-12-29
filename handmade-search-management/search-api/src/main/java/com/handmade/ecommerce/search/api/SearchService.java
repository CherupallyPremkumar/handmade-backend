package com.handmade.ecommerce.search.api;

import com.handmade.ecommerce.search.api.model.IndexRequest;
import com.handmade.ecommerce.search.api.model.SearchRequest;
import com.handmade.ecommerce.search.api.model.SearchResponse;

import java.util.List;

/**
 * Main search service interface.
 * Provides search, indexing, and reindexing capabilities.
 */
public interface SearchService {
    
    /**
     * Execute a search query
     * 
     * @param request Search request with query, filters, facets, etc.
     * @param resultType Class of result entities
     * @return Search response with results, facets, and metadata
     */
    <T> SearchResponse<T> search(SearchRequest request, Class<T> resultType);
    
    /**
     * Index a single document
     * 
     * @param request Index request with entity type, ID, and document
     */
    void index(IndexRequest request);
    
    /**
     * Index multiple documents in bulk
     * 
     * @param requests List of index requests
     */
    void bulkIndex(List<IndexRequest> requests);
    
    /**
     * Delete a document from the index
     * 
     * @param entityType Entity type (PRODUCT, SELLER, ORDER)
     * @param entityId Entity ID
     */
    void delete(String entityType, String entityId);
    
    /**
     * Reindex all documents of a given entity type
     * 
     * @param entityType Entity type to reindex
     */
    void reindex(String entityType);
    
    /**
     * Get search statistics
     * 
     * @return Map of statistics (index size, document count, etc.)
     */
    java.util.Map<String, Object> getStats();
}
