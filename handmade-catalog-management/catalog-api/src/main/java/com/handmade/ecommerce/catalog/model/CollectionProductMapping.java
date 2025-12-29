package com.handmade.ecommerce.catalog.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

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
public class CollectionProductMapping extends BaseJpaEntity {

    @Column(name = "collection_id", nullable = false, length = 50)
    private String collectionId;

    @Column(name = "product_id", nullable = false, length = 50)
    private String productId;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "added_by", length = 50)
    private String addedBy;

    // Getters and Setters

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
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

    @Override
    protected String getPrefix() {
        return "CPM"; // CollectionProductMapping prefix for ID generation
    }
}
