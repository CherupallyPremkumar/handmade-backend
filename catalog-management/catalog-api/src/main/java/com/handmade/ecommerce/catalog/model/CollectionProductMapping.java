package com.handmade.ecommerce.catalog.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * CollectionProductMapping - Entity
 * Many-to-many relationship between collections and products
 * Allows a product to belong to multiple collections
 */
@Entity
@Table(name = "collection_product_mapping", indexes = {
    @Index(name = "idx_collection_id", columnList = "collection_id"),
    @Index(name = "idx_product_id", columnList = "product_id")
})
public class CollectionProductMapping {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapping_id")
    private Long mappingId;
    
    @Column(name = "collection_id", nullable = false, length = 50)
    private String collectionId;
    
    @Column(name = "product_id", nullable = false, length = 50)
    private String productId;
    
    @Column(name = "display_order")
    private Integer displayOrder;
    
    @Column(name = "added_at")
    private LocalDateTime addedAt;
    
    @Column(name = "added_by", length = 50)
    private String addedBy; // User ID who added this product to collection
    
    // Getters and Setters
    
    public Long getMappingId() {
        return mappingId;
    }
    
    public void setMappingId(Long mappingId) {
        this.mappingId = mappingId;
    }
    
    public String getCollectionId() {
        return collectionId;
    }
    
    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public void setProductId(String productId) {
        this.productId = productId;
    }
    
    public Integer getDisplayOrder() {
        return displayOrder;
    }
    
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
    
    public LocalDateTime getAddedAt() {
        return addedAt;
    }
    
    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }
    
    public String getAddedBy() {
        return addedBy;
    }
    
    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }
    
    @PrePersist
    protected void onCreate() {
        addedAt = LocalDateTime.now();
    }
}
