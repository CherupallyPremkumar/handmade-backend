package com.handmade.ecommerce.domain.inventory;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * InboundShipment - Inbound inventory shipment managed by STM
 */
@Entity(name = "DomainInboundShipment")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_inbound_shipment")
public class InboundShipment extends AbstractJpaStateEntity {

    @Column(name = "destination_node_id", length = 36, nullable = false)
    private String fulfillmentNodeId;

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "shipment_reference", length = 100)
    private String shipmentReference;

    @Column(name = "expected_arrival_date")
    private Date expectedArrivalDate;

    @Column(name = "actual_arrival_date")
    private Date actualArrivalDate;

    @Column(name = "carrier_name", length = 100)
    private String carrier;

    @Column(name = "tracking_number", length = 255)
    private String trackingNumber;
}
