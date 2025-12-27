package com.handmade.ecommerce.search.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pagination settings for search requests
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pagination {
    
    /**
     * Page number (1-indexed)
     */
    @Builder.Default
    private int page = 1;
    
    /**
     * Page size (number of results per page)
     */
    @Builder.Default
    private int size = 20;
    
    /**
     * Maximum page size allowed
     */
    public static final int MAX_PAGE_SIZE = 100;
    
    /**
     * Get offset for database queries (0-indexed)
     */
    public int getOffset() {
        return (page - 1) * size;
    }
    
    /**
     * Validate and normalize pagination values
     */
    public void validate() {
        if (page < 1) {
            page = 1;
        }
        if (size < 1) {
            size = 20;
        }
        if (size > MAX_PAGE_SIZE) {
            size = MAX_PAGE_SIZE;
        }
    }
}
