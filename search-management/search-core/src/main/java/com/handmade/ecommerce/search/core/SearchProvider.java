package com.handmade.ecommerce.search.core;

import com.handmade.ecommerce.search.model.AutocompleteResult;
import com.handmade.ecommerce.search.model.SearchQuery;
import com.handmade.ecommerce.search.model.SearchResult;
import java.util.List;
import java.util.Map;

/**
 * SearchProvider - Plugin interface for search implementations
 * Enables Amazon-style search with Elasticsearch, Solr, Algolia, etc.
 */
public interface SearchProvider {
    
    /**
     * Get provider code (e.g., "ELASTICSEARCH", "SOLR", "ALGOLIA")
     */
    String getProviderCode();
    
    /**
     * Get provider name
     */
    String getProviderName();
    
    /**
     * Check if provider is enabled
     */
    boolean isEnabled();
    
    /**
     * Search products with filters, facets, sorting
     * 
     * @param query Search query
     * @return Search result with hits, facets, suggestions
     */
    SearchResult search(SearchQuery query);
    
    /**
     * Autocomplete suggestions (like Amazon search bar)
     * 
     * @param prefix Search prefix
     * @param limit Max suggestions
     * @return Autocomplete result with suggestions, products, categories
     */
    AutocompleteResult autocomplete(String prefix, int limit);
    
    /**
     * Get "Did you mean" suggestions for misspelled queries
     * 
     * @param query Original query
     * @param limit Max suggestions
     * @return List of suggested corrections
     */
    List<String> getSuggestions(String query, int limit);
    
    /**
     * Get related/similar search queries
     * 
     * @param query Original query
     * @param limit Max related queries
     * @return List of related search queries
     */
    List<String> getRelatedSearches(String query, int limit);
    
    /**
     * Index a document (product, category, etc.)
     * 
     * @param index Index name
     * @param id Document ID
     * @param document Document data
     */
    void indexDocument(String index, String id, Map<String, Object> document);
    
    /**
     * Bulk index documents
     * 
     * @param index Index name
     * @param documents Map of document ID -> document data
     */
    void bulkIndex(String index, Map<String, Map<String, Object>> documents);
    
    /**
     * Update document
     * 
     * @param index Index name
     * @param id Document ID
     * @param updates Partial updates
     */
    void updateDocument(String index, String id, Map<String, Object> updates);
    
    /**
     * Delete document
     * 
     * @param index Index name
     * @param id Document ID
     */
    void deleteDocument(String index, String id);
    
    /**
     * Create index with mapping
     * 
     * @param index Index name
     * @param mapping Field mappings
     */
    void createIndex(String index, Map<String, Object> mapping);
    
    /**
     * Delete index
     * 
     * @param index Index name
     */
    void deleteIndex(String index);
    
    /**
     * Check if index exists
     * 
     * @param index Index name
     * @return true if exists
     */
    boolean indexExists(String index);
    
    /**
     * Refresh index (make changes searchable)
     * 
     * @param index Index name
     */
    void refreshIndex(String index);
    
    /**
     * Get document count
     * 
     * @param index Index name
     * @return Document count
     */
    long getDocumentCount(String index);
}
