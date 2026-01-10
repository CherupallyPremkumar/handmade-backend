package com.handmade.ecommerce.catalog.product.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_product_browse_node
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_product_browse_node")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ProductBrowseNode extends BaseJpaEntity {
    
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "browse_node_id", nullable = false, length = 36)
    private String browseNodeId;
    @Column(name = "is_primary")
    private Boolean isPrimary;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
