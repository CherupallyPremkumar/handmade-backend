package com.handmade.ecommerce.search.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Sort criteria for search results
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SortCriteria {
    
    /**
     * Field to sort by (e.g., "relevance", "price", "createdAt")
     */
    @Builder.Default
    private String field = "relevance";
    
    /**
     * Sort order: ASC or DESC
     */
    @Builder.Default
    private SortOrder order = SortOrder.DESC;
    
    public enum SortOrder {
        ASC,
        DESC
    }
}
