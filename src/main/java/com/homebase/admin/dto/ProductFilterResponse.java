package com.homebase.admin.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * Response DTO for product filtering
 * Contains products and metadata for the home page
 */
public class ProductFilterResponse {
    
    private List<ProductDTO> products;
    private Set<String> categories;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private long totalProducts;
    private int currentPage;
    private int totalPages;

    public ProductFilterResponse() {
    }

    public ProductFilterResponse(List<ProductDTO> products, Set<String> categories, 
                                BigDecimal minPrice, BigDecimal maxPrice, 
                                long totalProducts, int currentPage, int totalPages) {
        this.products = products;
        this.categories = categories;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.totalProducts = totalProducts;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }

    // Getters and Setters
    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
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

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
