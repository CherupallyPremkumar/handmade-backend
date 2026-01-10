package com.handmade.ecommerce.catalog.comparison.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_comparison_item
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_comparison_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ComparisonItem extends BaseJpaEntity {
    
    @Column(name = "comparison_id", nullable = false, length = 36)
    private String comparisonId;
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
