package com.handmade.ecommerce.catalog.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * ProductCategoryMapping - Entity
 * Many-to-many relationship between products and categories
 * Allows a product to belong to multiple categories
 */
@Entity
@Table(name = "product_category_mapping", indexes = {
    @Index(name = "idx_product_id", columnList = "product_id"),
    @Index(name = "idx_category_id", columnList = "category_id"),
    @Index(name = "idx_primary_category", columnList = "is_primary")
})
public class ProductCategoryMapping {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapping_id")
    private Long mappingId;
    
    @Column(name = "product_id", nullable = false, length = 50)
    private String productId;
    
    @Column(name = "category_id", nullable = false, length = 50)
    private String categoryId;
    
    @Column(name = "is_primary")
    private Boolean isPrimary = false; // One category should be primary
    
    @Column(name = "display_order")
    private Integer displayOrder;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Getters and Setters
    
    public Long getMappingId() {
        return mappingId;
    }
    
    public void setMappingId(Long mappingId) {
        this.mappingId = mappingId;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public String getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    
    public Boolean getIsPrimary() {
        return isPrimary;
    }
    
    public void setIsPrimary(Boolean isPrimary) {
        this.isPrimary = isPrimary;
    }
    
    public Integer getDisplayOrder() {
        return displayOrder;
    }
    
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
