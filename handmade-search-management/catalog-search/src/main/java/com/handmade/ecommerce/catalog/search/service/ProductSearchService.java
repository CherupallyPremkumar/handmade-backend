package com.handmade.ecommerce.catalog.search.service;

import com.handmade.ecommerce.catalog.search.model.CatalogEntry;
import com.handmade.ecommerce.catalog.search.repository.CatalogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSearchService {
    
    @Autowired
    private CatalogEntryRepository catalogEntryRepository;
    
    public com.handmade.ecommerce.search.api.model.SearchResponse<CatalogEntry> search(com.handmade.ecommerce.search.api.model.SearchRequest request) {
        // Sanitize query
        String sanitizedQuery = sanitizeQuery(request.getQuery());
        
        // Extract categoryId from filters if present
        String categoryId = null;
        if (request.getFilters() != null && request.getFilters().containsKey("categoryId")) {
            categoryId = (String) request.getFilters().get("categoryId");
        }
        
        // Execute search
        List<CatalogEntry> results;
        int page = 0;
        int pageSize = 20;
        
        if (request.getPagination() != null) {
            page = request.getPagination().getPage();
            pageSize = request.getPagination().getSize();
        }

        if (categoryId != null) {
            results = catalogEntryRepository.searchByCategoryAndQuery(
                categoryId,
                sanitizedQuery,
                pageSize,
                page * pageSize
            );
        } else {
            results = catalogEntryRepository.searchByQuery(
                sanitizedQuery,
                pageSize,
                page * pageSize
            );
        }
        
        return com.handmade.ecommerce.search.api.model.SearchResponse.<CatalogEntry>builder()
            .results(results)
            .totalHits(results.size()) // Simplification for demo
            .build();
    }
    
    public List<CatalogEntry> getTrending() {
        return catalogEntryRepository.findTop20ByOrderByPopularityDesc();
    }
    
    /**
     * Sanitize user input for PostgreSQL tsquery
     * Converts "handmade basket" to "handmade & basket"
     */
    private String sanitizeQuery(String query) {
        return query.trim()
            .replaceAll("[^a-zA-Z0-9\\s]", "")  // Remove special chars
            .replaceAll("\\s+", " & ");         // AND operator
    }
}
