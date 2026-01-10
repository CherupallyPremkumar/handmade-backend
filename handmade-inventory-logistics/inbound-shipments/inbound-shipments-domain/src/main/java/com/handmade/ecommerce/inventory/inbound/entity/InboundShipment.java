package com.handmade.ecommerce.inventory.inbound.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_inbound_shipment
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_inbound_shipment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class InboundShipment extends BaseJpaEntity {
    
    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "destination_node_id", nullable = false, length = 36)
    private String destinationNodeId;
    @Column(name = "ship_from_address_id", length = 36)
    private String shipFromAddressId;
    @Column(name = "status", length = 50)
    private String status;
    @Column(name = "label_prep_type", length = 50)
    private String labelPrepType;
    @Column(name = "carrier_name", length = 100)
    private String carrierName;
    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
