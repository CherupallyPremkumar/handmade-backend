package com.handmade.ecommerce.inventory.operations.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_pack_task
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_pack_task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PackTask extends BaseJpaEntity {
    
    @Column(name = "fulfillment_node_id", nullable = false, length = 36)
    private String fulfillmentNodeId;
    @Column(name = "pick_task_id", nullable = false, length = 36)
    private String pickTaskId;
    @Column(name = "shipment_id", length = 36)
    private String shipmentId;
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
