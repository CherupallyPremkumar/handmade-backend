package com.handmade.ecommerce.inventory.operations.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_pick_task
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_pick_task")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PickTask extends BaseJpaEntity {
    
    @Column(name = "fulfillment_node_id", nullable = false, length = 36)
    private String fulfillmentNodeId;
    @Column(name = "order_id", nullable = false, length = 36)
    private String orderId;
    @Column(name = "assigned_to_user_id", length = 36)
    private String assignedToUserId;
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    @Column(name = "priority", nullable = false)
    private String priority;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
