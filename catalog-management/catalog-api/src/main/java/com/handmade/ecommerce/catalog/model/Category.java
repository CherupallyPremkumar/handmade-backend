package com.handmade.ecommerce.catalog.model;

import jakarta.persistence.*;
import org.chenile.utils.entity.model.ChenileEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Category - Aggregate Root
 * Represents a hierarchical category structure for organizing products
 */
@Entity
@Table(name = "categories")
public class Category extends ChenileEntity {
    
    @Id
    @Column(name = "category_id", length = 50)
    private String categoryId;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "slug", nullable = false, unique = true, length = 100)
    private String slug; // URL-friendly: "handmade-jewelry"
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Column(name = "parent_id", length = 50)
    private String parentId; // For hierarchy
    
    @Column(name = "level")
    private Integer level; // 0=root, 1=child, 2=grandchild
    
    @Column(name = "display_order")
    private Integer displayOrder;
    
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    @Column(name = "icon", length = 100)
    private String icon; // Icon class or name
    
    @Column(name = "active")
    private Boolean active = true;
    
    @Column(name = "featured")
    private Boolean featured = false;
    
    @Column(name = "product_count")
    private Long productCount = 0L; // Denormalized for performance
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Embedded
    private CategoryMetadata metadata;
    
    // Transient field for child categories (not persisted)
    @Transient
    private List<Category> children = new ArrayList<>();
    
    // Getters and Setters
    
    public String getCategoryId() {
        return categoryId;
    }
    
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
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
    
    public String getParentId() {
        return parentId;
    }
    
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    
    public Integer getLevel() {
        return level;
    }
    
    public void setLevel(Integer level) {
        this.level = level;
    }
    
    public Integer getDisplayOrder() {
        return displayOrder;
    }
    
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
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
    
    public CategoryMetadata getMetadata() {
        return metadata;
    }
    
    public void setMetadata(CategoryMetadata metadata) {
        this.metadata = metadata;
    }
    
    public List<Category> getChildren() {
        return children;
    }
    
    public void setChildren(List<Category> children) {
        this.children = children;
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
}
