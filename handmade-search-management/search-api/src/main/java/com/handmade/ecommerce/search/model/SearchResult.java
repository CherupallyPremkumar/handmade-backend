package com.handmade.ecommerce.search.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SearchResult - Amazon-style search result with facets, suggestions, analytics
 */
public class SearchResult {
    
    private List<SearchHit> hits;                   // Search results
    private long totalHits;                         // Total matching documents
    private int page;                               // Current page
    private int size;                               // Page size
    private long took;                              // Query time (ms)
    private Map<String, Facet> facets;              // Facet aggregations
    private List<String> suggestions;               // "Did you mean" suggestions
    private List<String> relatedSearches;           // Related search queries
    private Map<String, Object> metadata;           // Additional metadata
    
    public SearchResult() {
        this.hits = new ArrayList<>();
        this.facets = new HashMap<>();
        this.suggestions = new ArrayList<>();
        this.relatedSearches = new ArrayList<>();
        this.metadata = new HashMap<>();
    }
    
    // Getters and Setters
    
    public List<SearchHit> getHits() {
        return hits;
    }
    
    public void setHits(List<SearchHit> hits) {
        this.hits = hits;
    }
    
    public void addHit(SearchHit hit) {
        this.hits.add(hit);
    }
    
    public long getTotalHits() {
        return totalHits;
    }
    
    public void setTotalHits(long totalHits) {
        this.totalHits = totalHits;
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
    
    public long getTook() {
        return took;
    }
    
    public void setTook(long took) {
        this.took = took;
    }
    
    public Map<String, Facet> getFacets() {
        return facets;
    }
    
    public void setFacets(Map<String, Facet> facets) {
        this.facets = facets;
    }
    
    public void addFacet(String name, Facet facet) {
        this.facets.put(name, facet);
    }
    
    public List<String> getSuggestions() {
        return suggestions;
    }
    
    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
    
    public List<String> getRelatedSearches() {
        return relatedSearches;
    }
    
    public void setRelatedSearches(List<String> relatedSearches) {
        this.relatedSearches = relatedSearches;
    }
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
    
    public int getTotalPages() {
        return (int) Math.ceil((double) totalHits / size);
    }
    
    public boolean hasNextPage() {
        return page < getTotalPages() - 1;
    }
    
    public boolean hasPreviousPage() {
        return page > 0;
    }
    
    /**
     * SearchHit - Individual search result
     */
    public static class SearchHit {
        private String id;
        private double score;                       // Relevance score
        private Map<String, Object> source;         // Document data
        private Map<String, List<String>> highlights; // Highlighted snippets
        
        public SearchHit() {
            this.source = new HashMap<>();
            this.highlights = new HashMap<>();
        }
        
        public SearchHit(String id, double score, Map<String, Object> source) {
            this.id = id;
            this.score = score;
            this.source = source;
            this.highlights = new HashMap<>();
        }
        
        public String getId() { return id; }
        public void setId(String id) { this.id = id; }
        public double getScore() { return score; }
        public void setScore(double score) { this.score = score; }
        public Map<String, Object> getSource() { return source; }
        public void setSource(Map<String, Object> source) { this.source = source; }
        public Map<String, List<String>> getHighlights() { return highlights; }
        public void setHighlights(Map<String, List<String>> highlights) { this.highlights = highlights; }
    }
    
    /**
     * Facet - Aggregation result (e.g., category counts, price ranges)
     */
    public static class Facet {
        private String name;
        private List<FacetBucket> buckets;
        
        public Facet() {
            this.buckets = new ArrayList<>();
        }
        
        public Facet(String name) {
            this.name = name;
            this.buckets = new ArrayList<>();
        }
        
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public List<FacetBucket> getBuckets() { return buckets; }
        public void setBuckets(List<FacetBucket> buckets) { this.buckets = buckets; }
        public void addBucket(FacetBucket bucket) { this.buckets.add(bucket); }
    }
    
    /**
     * FacetBucket - Individual facet value with count
     */
    public static class FacetBucket {
        private String key;
        private long count;
        private boolean selected;
        
        public FacetBucket() {}
        
        public FacetBucket(String key, long count) {
            this.key = key;
            this.count = count;
        }
        
        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }
        public long getCount() { return count; }
        public void setCount(long count) { this.count = count; }
        public boolean isSelected() { return selected; }
        public void setSelected(boolean selected) { this.selected = selected; }
    }
}
