package com.homebase.admin.dto;

import java.math.BigDecimal;

/**
 * Request DTO for filtering products on the home page
 * Supports all frontend filter requirements
 */
public class ProductFilterRequest {
    
    private String category;
    private String searchQuery;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean inStockOnly;
    private Boolean onSaleOnly;
    private String sortBy; // "name-asc", "name-desc", "price-asc", "price-desc", "rating"
    private Integer page = 0;
    private Integer size = 100;

    // Getters and Setters
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Boolean getInStockOnly() {
        return inStockOnly;
    }

    public void setInStockOnly(Boolean inStockOnly) {
        this.inStockOnly = inStockOnly;
    }

    public Boolean getOnSaleOnly() {
        return onSaleOnly;
    }

    public void setOnSaleOnly(Boolean onSaleOnly) {
        this.onSaleOnly = onSaleOnly;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
