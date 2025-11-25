package com.handmade.ecommerce.catalog.model;

import jakarta.persistence.*;
import org.chenile.utils.entity.model.ChenileEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * CatalogItem - Aggregate Root
 * Maps products to categories and collections
 * This is the bridge between Product Management and Catalog Management
 */
@Entity
@Table(name = "catalog_items", indexes = {
    @Index(name = "idx_product_id", columnList = "product_id"),
    @Index(name = "idx_featured", columnList = "featured"),
    @Index(name = "idx_active", columnList = "active")
})
public class CatalogItem extends ChenileEntity {
    
    @Id
    @Column(name = "catalog_item_id", length = 50)
    private String catalogItemId;
    
    /**
     * Reference to Product from product-management module
     * This is the ONLY connection to Product Management
     */
    @Column(name = "product_id", nullable = false, length = 50)
    private String productId;
    
    @Column(name = "featured")
    private Boolean featured = false;
    
    @Column(name = "display_order")
    private Integer displayOrder;
    
    @Column(name = "active")
    private Boolean active = true;
    
    @Column(name = "visibility_start_date")
    private LocalDateTime visibilityStartDate;
    
    @Column(name = "visibility_end_date")
    private LocalDateTime visibilityEndDate;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Merchandising tags for filtering and search
     * Examples: "trending", "bestseller", "new", "sale"
     */
    @ElementCollection
    @CollectionTable(name = "catalog_item_tags", joinColumns = @JoinColumn(name = "catalog_item_id"))
    @Column(name = "tag", length = 50)
    private List<String> tags = new ArrayList<>();
    
    // Transient fields (loaded separately for performance)
    @Transient
    private List<String> categoryIds = new ArrayList<>();
    
    @Transient
    private List<String> collectionIds = new ArrayList<>();
    
    // Getters and Setters
    
    public String getCatalogItemId() {
        return catalogItemId;
    }
    
    public void setCatalogItemId(String catalogItemId) {
        this.catalogItemId = catalogItemId;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public Boolean getFeatured() {
        return featured;
    }
    
    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }
    
    public Integer getDisplayOrder() {
        return displayOrder;
    }
    
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public LocalDateTime getVisibilityStartDate() {
        return visibilityStartDate;
    }
    
    public void setVisibilityStartDate(LocalDateTime visibilityStartDate) {
        this.visibilityStartDate = visibilityStartDate;
    }
    
    public LocalDateTime getVisibilityEndDate() {
        return visibilityEndDate;
    }
    
    public void setVisibilityEndDate(LocalDateTime visibilityEndDate) {
        this.visibilityEndDate = visibilityEndDate;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public List<String> getTags() {
        return tags;
    }
    
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    
    public List<String> getCategoryIds() {
        return categoryIds;
    }
    
    public void setCategoryIds(List<String> categoryIds) {
        this.categoryIds = categoryIds;
    }
    
    public List<String> getCollectionIds() {
        return collectionIds;
    }
    
    public void setCollectionIds(List<String> collectionIds) {
        this.collectionIds = collectionIds;
    }
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    /**
     * Check if item is currently visible based on date range
     */
    public boolean isCurrentlyVisible() {
        if (!active) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        if (visibilityStartDate != null && now.isBefore(visibilityStartDate)) {
            return false;
        }
        if (visibilityEndDate != null && now.isAfter(visibilityEndDate)) {
            return false;
        }
        return true;
    }
}
