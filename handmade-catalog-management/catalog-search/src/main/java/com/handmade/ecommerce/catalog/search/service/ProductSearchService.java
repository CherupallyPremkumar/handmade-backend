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
    
    public SearchResponse search(SearchRequest request) {
        // Sanitize query for PostgreSQL tsquery
        String sanitizedQuery = sanitizeQuery(request.getQuery());
        
        // Execute search
        List<CatalogEntry> results;
        if (request.getCategoryId() != null) {
            results = catalogEntryRepository.searchByCategoryAndQuery(
                request.getCategoryId(),
                sanitizedQuery,
                request.getPageSize(),
                request.getPage() * request.getPageSize()
            );
        } else {
            results = catalogEntryRepository.searchByQuery(
                sanitizedQuery,
                request.getPageSize(),
                request.getPage() * request.getPageSize()
            );
        }
        
        return SearchResponse.builder()
            .results(results)
            .page(request.getPage())
            .pageSize(request.getPageSize())
            .totalResults(results.size())
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
