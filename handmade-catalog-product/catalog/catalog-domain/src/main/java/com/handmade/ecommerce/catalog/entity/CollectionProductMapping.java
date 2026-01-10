package com.handmade.ecommerce.catalog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_collection_product_mapping
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_collection_product_mapping")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CollectionProductMapping extends BaseJpaEntity {
    
    @Column(name = "collection_id", nullable = false, length = 36)
    private String collectionId;
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "display_order")
    private String displayOrder;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
