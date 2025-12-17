package com.handmade.ecommerce.catalog.search.controller;

import com.handmade.ecommerce.catalog.search.model.CatalogEntry;
import com.handmade.ecommerce.catalog.search.service.ProductSearchService;
import com.handmade.ecommerce.catalog.search.service.SearchRequest;
import com.handmade.ecommerce.catalog.search.service.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")
public class SearchController {
    
    @Autowired
    private ProductSearchService searchService;
    
    /**
     * Full-text search for products
     * GET /api/search/products?q=handmade&categoryId=cat-001&page=0
     */
    @GetMapping("/products")
    public ResponseEntity<SearchResponse> search(
            @RequestParam String q,
            @RequestParam(required = false) String categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int pageSize) {
        
        SearchRequest request = SearchRequest.builder()
            .query(q)
            .categoryId(categoryId)
            .page(page)
            .pageSize(pageSize)
            .build();
        
        return ResponseEntity.ok(searchService.search(request));
    }
    
    /**
     * Get trending products
     * GET /api/search/trending
     */
    @GetMapping("/trending")
    public ResponseEntity<List<CatalogEntry>> getTrending() {
        return ResponseEntity.ok(searchService.getTrending());
    }
}
