package com.handmade.ecommerce.inventory.inbound.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_inbound_shipment_item
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_inbound_shipment_item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class InboundShipmentItem extends BaseJpaEntity {
    
    @Column(name = "shipment_id", nullable = false, length = 36)
    private String shipmentId;
    @Column(name = "seller_sku", nullable = false, length = 100)
    private String sellerSku;
    @Column(name = "fulfillment_network_sku", length = 100)
    private String fulfillmentNetworkSku;
    @Column(name = "quantity_shipped", nullable = false)
    private String quantityShipped;
    @Column(name = "quantity_received")
    private String quantityReceived;
    @Column(name = "quantity_in_case")
    private String quantityInCase;
    @Column(name = "prep_details", length = 255)
    private String prepDetails;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
