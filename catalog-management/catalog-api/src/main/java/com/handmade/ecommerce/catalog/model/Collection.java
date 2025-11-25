package com.handmade.ecommerce.catalog.model;

import jakarta.persistence.*;
import org.chenile.utils.entity.model.ChenileEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Collection - Aggregate Root
 * Represents curated collections of products (e.g., "New Arrivals", "Holiday Gifts")
 */
@Entity
@Table(name = "collections")
public class Collection extends ChenileEntity {
    
    @Id
    @Column(name = "collection_id", length = 50)
    private String collectionId;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "slug", nullable = false, unique = true, length = 100)
    private String slug;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 20)
    private CollectionType type; // SEASONAL, FEATURED, CURATED, DYNAMIC
    
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    @Column(name = "start_date")
    private LocalDateTime startDate;
    
    @Column(name = "end_date")
    private LocalDateTime endDate;
    
    @Column(name = "active")
    private Boolean active = true;
    
    @Column(name = "featured")
    private Boolean featured = false;
    
    @Column(name = "display_order")
    private Integer displayOrder;
    
    @Column(name = "auto_update")
    private Boolean autoUpdate = false; // For dynamic collections
    
    @Column(name = "rule_expression", length = 1000)
    private String ruleExpression; // For dynamic collections: "products.createdAt > now() - 7 days"
    
    @Column(name = "product_count")
    private Long productCount = 0L;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Transient field for product IDs (loaded separately for performance)
    @Transient
    private List<String> productIds = new ArrayList<>();
    
    // Getters and Setters
    
    public String getCollectionId() {
        return collectionId;
    }
    
    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getSlug() {
        return slug;
    }
    
    public void setSlug(String slug) {
        this.slug = slug;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public CollectionType getType() {
        return type;
    }
    
    public void setType(CollectionType type) {
        this.type = type;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public LocalDateTime getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
    
    public LocalDateTime getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
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
    
    public Boolean getAutoUpdate() {
        return autoUpdate;
    }
    
    public void setAutoUpdate(Boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
    }
    
    public String getRuleExpression() {
        return ruleExpression;
    }
    
    public void setRuleExpression(String ruleExpression) {
        this.ruleExpression = ruleExpression;
    }
    
    public Long getProductCount() {
        return productCount;
    }
    
    public void setProductCount(Long productCount) {
        this.productCount = productCount;
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
    
    public List<String> getProductIds() {
        return productIds;
    }
    
    public void setProductIds(List<String> productIds) {
        this.productIds = productIds;
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
     * Check if collection is currently active based on date range
     */
    public boolean isCurrentlyActive() {
        LocalDateTime now = LocalDateTime.now();
        if (startDate != null && now.isBefore(startDate)) {
            return false;
        }
        if (endDate != null && now.isAfter(endDate)) {
            return false;
        }
        return active;
    }
}
