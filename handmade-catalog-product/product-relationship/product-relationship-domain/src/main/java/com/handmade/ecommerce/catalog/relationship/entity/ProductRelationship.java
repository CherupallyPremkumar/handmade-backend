package com.handmade.ecommerce.catalog.relationship.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_product_relationship
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_product_relationship")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductRelationship extends BaseJpaEntity {
    
    @Column(name = "parent_id", nullable = false, length = 36)
    private String parentId;
    @Column(name = "child_id", nullable = false, length = 36)
    private String childId;
    @Column(name = "type", nullable = false, length = 50)
    private String type;
    @Column(name = "metadata")
    private String metadata;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
