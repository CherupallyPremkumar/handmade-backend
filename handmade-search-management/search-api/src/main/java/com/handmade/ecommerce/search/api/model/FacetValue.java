package com.handmade.ecommerce.search.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Facet value with count for aggregated search results
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FacetValue {
    
    /**
     * Facet value (e.g., "accessories", "1000-2000")
     */
    private String value;
    
    /**
     * Number of results with this facet value
     */
    private long count;
    
    /**
     * Display label (optional, for UI)
     */
    private String label;
}
