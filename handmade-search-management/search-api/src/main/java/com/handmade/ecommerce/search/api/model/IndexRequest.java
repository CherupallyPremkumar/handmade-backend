package com.handmade.ecommerce.search.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Request to index a document in the search engine
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexRequest {
    
    /**
     * Entity type (PRODUCT, SELLER, ORDER)
     */
    private String entityType;
    
    /**
     * Entity ID (unique identifier)
     */
    private String entityId;
    
    /**
     * Document to index (field-value pairs)
     */
    private Map<String, Object> document;
    
    /**
     * Whether to refresh index immediately (default: false for async)
     */
    @Builder.Default
    private boolean refreshImmediately = false;
}
