package com.handmade.ecommerce.catalog.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_browse_node_attribute_rule
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_browse_node_attribute_rule")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class BrowseNodeAttributeRule extends BaseJpaEntity {
    
    @Column(name = "browse_node_id", nullable = false, length = 36)
    private String browseNodeId;
    @Column(name = "attribute_id", nullable = false, length = 36)
    private String attributeId;
    @Column(name = "is_required")
    private Boolean isRequired;
    @Column(name = "is_variation_axis")
    private Boolean isVariationAxis;
    @Column(name = "display_order")
    private String displayOrder;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
