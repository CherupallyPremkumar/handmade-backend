package com.handmade.ecommerce.catalog.model;

import jakarta.persistence.*;


import java.time.LocalDateTime;

/**
 * Catalog Entry Entity
 * Represents indexed product data for search and discovery
 */
@Entity
@Table(name = "hm_catalog_entry", indexes = {
    @Index(name = "idx_catalog_product", columnList = "product_id"),
    @Index(name = "idx_catalog_category", columnList = "category_id"),
    @Index(name = "idx_catalog_popularity", columnList = "popularity DESC")
})

public class CatalogEntry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "product_id", nullable = false, unique = true)
    private Long productId;
    
    @Column(name = "category_id", nullable = false)
    private Long categoryId;
    
    @Column(name = "search_keywords", columnDefinition = "TEXT")
    private String searchKeywords;
    
    /**
     * PostgreSQL TSVector for full-text search
     * Generated from product name, description, tags
     */
    @Column(name = "search_vector", columnDefinition = "tsvector")
    private String searchVector;
    
    @Column(name = "popularity", nullable = false)
    private Integer popularity = 0;
    
    @Column(name = "view_count", nullable = false)
    private Long viewCount = 0L;
    
    @Column(name = "sales_count", nullable = false)
    private Long salesCount = 0L;
    
    @Column(name = "indexed_at")
    private LocalDateTime indexedAt;
    
    /**
     * Increment view count
     */
    public void incrementViewCount() {
        this.viewCount++;
        updatePopularity();
    }
    
    /**
     * Increment sales count
     */
    public void incrementSalesCount() {
        this.salesCount++;
        updatePopularity();
    }
    
    /**
     * Update popularity score based on views and sales
     * Formula: (sales * 10) + (views / 100)
     */
    private void updatePopularity() {
        this.popularity = (int) ((salesCount * 10) + (viewCount / 100));
    }
    
    @PrePersist
    @PreUpdate
    protected void onSave() {
        indexedAt = LocalDateTime.now();
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getSearchKeywords() {
        return searchKeywords;
    }

    public void setSearchKeywords(String searchKeywords) {
        this.searchKeywords = searchKeywords;
    }

    public String getSearchVector() {
        return searchVector;
    }

    public void setSearchVector(String searchVector) {
        this.searchVector = searchVector;
    }

    public Integer getPopularity() {
        return popularity;
    }

    public void setPopularity(Integer popularity) {
        this.popularity = popularity;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Long getSalesCount() {
        return salesCount;
    }

    public void setSalesCount(Long salesCount) {
        this.salesCount = salesCount;
    }

    public LocalDateTime getIndexedAt() {
        return indexedAt;
    }

    public void setIndexedAt(LocalDateTime indexedAt) {
        this.indexedAt = indexedAt;
    }
}
