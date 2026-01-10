package com.handmade.ecommerce.catalog.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * Represents a relationship between two Catalog Items (Products).
 * Supports variations (Parent-Child), Accessories, Bundles, etc.
 * Maps to hm_product_relationship table.
 */
@Entity
@Table(name = "hm_product_relationship", indexes = {
        @Index(name = "idx_cr_parent_child", columnList = "parent_id, child_id", unique = true)
})
public class ProductRelationship extends BaseJpaEntity {

    @Column(name = "parent_id", nullable = false, length = 36)
    private String parentId;

    @Column(name = "child_id", nullable = false, length = 36)
    private String childId;

    /**
     * Relationship Type: VARIATION, ACCESSORY, NEWER_VERSION, BUNDLE
     */
    @Column(name = "type", nullable = false, length = 50)
    private String type;

    /**
     * JSON metadata describing the relationship
     * e.g. { "theme": "SIZE_COLOR", "details": "Blue/M" }
     */
    @Column(name = "metadata", columnDefinition = "text")
    private String metadata;

    // Getters and Setters

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
