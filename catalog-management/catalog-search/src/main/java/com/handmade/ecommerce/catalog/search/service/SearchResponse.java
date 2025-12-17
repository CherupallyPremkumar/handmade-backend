package com.handmade.ecommerce.catalog.search.service;

import com.handmade.ecommerce.catalog.search.model.CatalogEntry;
import java.util.List;

public class SearchResponse {
    private List<CatalogEntry> results;
    private int page;
    private int pageSize;
    private int totalResults;
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private List<CatalogEntry> results;
        private int page;
        private int pageSize;
        private int totalResults;
        
        public Builder results(List<CatalogEntry> results) {
            this.results = results;
            return this;
        }
        
        public Builder page(int page) {
            this.page = page;
            return this;
        }
        
        public Builder pageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }
        
        public Builder totalResults(int totalResults) {
            this.totalResults = totalResults;
            return this;
        }
        
        public SearchResponse build() {
            SearchResponse response = new SearchResponse();
            response.results = this.results;
            response.page = this.page;
            response.pageSize = this.pageSize;
            response.totalResults = this.totalResults;
            return response;
        }
    }
    
    // Getters
    public List<CatalogEntry> getResults() { return results; }
    public int getPage() { return page; }
    public int getPageSize() { return pageSize; }
    public int getTotalResults() { return totalResults; }
}
