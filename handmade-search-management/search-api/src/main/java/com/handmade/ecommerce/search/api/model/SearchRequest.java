package com.handmade.ecommerce.search.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Search request containing query, filters, facets, pagination, and sorting criteria.
 * Used across all search providers (Elasticsearch, Solr, etc.)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    
    /**
     * Search query text (e.g., "handmade leather bag")
     */
    private String query;
    
    /**
     * Entity type to search: PRODUCT, SELLER, ORDER
     */
    private String entityType;
    
    /**
     * Filters to apply (e.g., category, price range, seller ID)
     * Key: filter field name
     * Value: filter value (can be single value, range, or list)
     */
    private Map<String, Object> filters;
    
    /**
     * Facets to compute (e.g., category, price, seller)
     * Returns aggregated counts for each facet value
     */
    private List<String> facets;
    
    /**
     * Pagination settings
     */
    private Pagination pagination;
    
    /**
     * Sort criteria
     */
    private SortCriteria sort;
    
    /**
     * Whether to include highlights in results
     */
    private boolean includeHighlights;
    
    /**
     * Additional provider-specific options
     */
    private Map<String, Object> options;
}
