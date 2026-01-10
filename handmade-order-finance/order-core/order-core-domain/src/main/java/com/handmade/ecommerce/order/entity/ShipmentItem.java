package com.handmade.ecommerce.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_shipment_item
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_shipment_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ShipmentItem extends BaseJpaEntity {
    
    @Column(name = "shipment_id", nullable = false, length = 36)
    private String shipmentId;
    @Column(name = "order_line_id", nullable = false, length = 36)
    private String orderLineId;
    @Column(name = "quantity_shipped", nullable = false)
    private String quantityShipped;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
