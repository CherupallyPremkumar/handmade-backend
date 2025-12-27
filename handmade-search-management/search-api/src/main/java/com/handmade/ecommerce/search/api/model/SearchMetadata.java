package com.handmade.ecommerce.search.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Metadata about search execution
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchMetadata {
    
    /**
     * Time taken to execute search (milliseconds)
     */
    private long took;
    
    /**
     * Search provider used (e.g., "elasticsearch", "solr")
     */
    private String provider;
    
    /**
     * Query that was executed (for debugging)
     */
    private String executedQuery;
    
    /**
     * Whether results came from cache
     */
    private boolean fromCache;
}
