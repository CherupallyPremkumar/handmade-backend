package com.handmade.ecommerce.search.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Search response containing results, facets, and metadata.
 * Generic type T represents the result entity (Product, Seller, Order, etc.)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchResponse<T> {
    
    /**
     * Search results
     */
    private List<T> results;
    
    /**
     * Total number of hits (before pagination)
     */
    private long totalHits;
    
    /**
     * Facets with aggregated counts
     * Key: facet name (e.g., "category", "price")
     * Value: list of facet values with counts
     */
    private Map<String, List<FacetValue>> facets;
    
    /**
     * Search metadata (timing, provider, etc.)
     */
    private SearchMetadata metadata;
    
    /**
     * Highlighted snippets (if requested)
     */
    private Map<String, List<String>> highlights;
}
