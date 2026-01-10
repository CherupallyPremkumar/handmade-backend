package com.handmade.ecommerce.order.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * JPA Entity for hm_shipment
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_shipment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Shipment extends AbstractJpaStateEntity {
    
    @Column(name = "order_id", nullable = false, length = 36)
    private String orderId;
    @Column(name = "seller_id", length = 36)
    private String sellerId;
    @Column(name = "fulfillment_node_id", length = 36)
    private String fulfillmentNodeId;
    @Column(name = "status", length = 50)
    private String status;
    @Column(name = "shipping_method", length = 50)
    private String shippingMethod;
    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;
    @Column(name = "carrier", length = 50)
    private String carrier;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
