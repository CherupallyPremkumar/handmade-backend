package com.handmade.ecommerce.catalog.search.service;

public class SearchRequest {
    private String query;
    private String categoryId;
    private int page;
    private int pageSize;
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String query;
        private String categoryId;
        private int page;
        private int pageSize;
        
        public Builder query(String query) {
            this.query = query;
            return this;
        }
        
        public Builder categoryId(String categoryId) {
            this.categoryId = categoryId;
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
        
        public SearchRequest build() {
            SearchRequest request = new SearchRequest();
            request.query = this.query;
            request.categoryId = this.categoryId;
            request.page = this.page;
            request.pageSize = this.pageSize;
            return request;
        }
    }
    
    // Getters
    public String getQuery() { return query; }
    public String getCategoryId() { return categoryId; }
    public int getPage() { return page; }
    public int getPageSize() { return pageSize; }
}
