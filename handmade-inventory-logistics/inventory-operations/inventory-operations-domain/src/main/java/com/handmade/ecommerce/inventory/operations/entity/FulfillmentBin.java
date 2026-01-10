package com.handmade.ecommerce.inventory.operations.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_fulfillment_bin
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_fulfillment_bin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class FulfillmentBin extends BaseJpaEntity {
    
    @Column(name = "fulfillment_node_id", nullable = false, length = 36)
    private String fulfillmentNodeId;
    @Column(name = "bin_code", nullable = false, length = 50)
    private String binCode;
    @Column(name = "zone", length = 20)
    private String zone;
    @Column(name = "aisle", length = 20)
    private String aisle;
    @Column(name = "shelf", length = 20)
    private String shelf;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
