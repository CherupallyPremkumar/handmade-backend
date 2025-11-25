package com.handmade.ecommerce.search.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SearchQuery - Amazon-style search query with filters, facets, sorting
 */
public class SearchQuery {
    
    private String query;                           // Search text
    private String category;                        // Category filter
    private List<Filter> filters;                   // Price, rating, etc.
    private List<String> facets;                    // Fields to facet on
    private SortOption sortBy;                      // Sort option
    private int page;                               // Page number (0-indexed)
    private int size;                               // Results per page
    private boolean includeAggregations;            // Include facet counts
    private boolean includeHighlights;              // Highlight matching terms
    private Map<String, Object> metadata;           // Additional metadata
    
    public SearchQuery() {
        this.filters = new ArrayList<>();
        this.facets = new ArrayList<>();
        this.page = 0;
        this.size = 20;
        this.includeAggregations = true;
        this.includeHighlights = true;
        this.metadata = new HashMap<>();
    }
    
    // Getters and Setters
    
    public String getQuery() {
        return query;
    }
    
    public void setQuery(String query) {
        this.query = query;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public List<Filter> getFilters() {
        return filters;
    }
    
    public void setFilters(List<Filter> filters) {
        this.filters = filters;
    }
    
    public void addFilter(Filter filter) {
        this.filters.add(filter);
    }
    
    public List<String> getFacets() {
        return facets;
    }
    
    public void setFacets(List<String> facets) {
        this.facets = facets;
    }
    
    public void addFacet(String facet) {
        this.facets.add(facet);
    }
    
    public SortOption getSortBy() {
        return sortBy;
    }
    
    public void setSortBy(SortOption sortBy) {
        this.sortBy = sortBy;
    }
    
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    
    public boolean isIncludeAggregations() {
        return includeAggregations;
    }
    
    public void setIncludeAggregations(boolean includeAggregations) {
        this.includeAggregations = includeAggregations;
    }
    
    public boolean isIncludeHighlights() {
        return includeHighlights;
    }
    
    public void setIncludeHighlights(boolean includeHighlights) {
        this.includeHighlights = includeHighlights;
    }
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
    
    /**
     * Filter - Represents a search filter
     */
    public static class Filter {
        private String field;
        private FilterType type;
        private Object value;
        private Object minValue;
        private Object maxValue;
        
        public Filter() {}
        
        public Filter(String field, FilterType type, Object value) {
            this.field = field;
            this.type = type;
            this.value = value;
        }
        
        public static Filter range(String field, Object min, Object max) {
            Filter filter = new Filter();
            filter.field = field;
            filter.type = FilterType.RANGE;
            filter.minValue = min;
            filter.maxValue = max;
            return filter;
        }
        
        public static Filter term(String field, Object value) {
            return new Filter(field, FilterType.TERM, value);
        }
        
        public static Filter terms(String field, Object... values) {
            return new Filter(field, FilterType.TERMS, values);
        }
        
        // Getters and Setters
        public String getField() { return field; }
        public void setField(String field) { this.field = field; }
        public FilterType getType() { return type; }
        public void setType(FilterType type) { this.type = type; }
        public Object getValue() { return value; }
        public void setValue(Object value) { this.value = value; }
        public Object getMinValue() { return minValue; }
        public void setMinValue(Object minValue) { this.minValue = minValue; }
        public Object getMaxValue() { return maxValue; }
        public void setMaxValue(Object maxValue) { this.maxValue = maxValue; }
    }
    
    /**
     * FilterType - Type of filter
     */
    public enum FilterType {
        TERM,           // Exact match
        TERMS,          // Multiple values (OR)
        RANGE,          // Range (min-max)
        EXISTS,         // Field exists
        PREFIX,         // Starts with
        WILDCARD        // Pattern match
    }
    
    /**
     * SortOption - Sorting options
     */
    public enum SortOption {
        RELEVANCE,              // Best match (default)
        PRICE_LOW_TO_HIGH,      // Price ascending
        PRICE_HIGH_TO_LOW,      // Price descending
        NEWEST_FIRST,           // Creation date descending
        RATING_HIGH_TO_LOW,     // Rating descending
        POPULARITY,             // Most popular
        NAME_A_TO_Z,            // Name ascending
        NAME_Z_TO_A             // Name descending
    }
}
