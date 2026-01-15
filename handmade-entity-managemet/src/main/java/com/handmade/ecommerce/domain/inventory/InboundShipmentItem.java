package com.handmade.ecommerce.domain.inventory;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * InboundShipmentItem - Items in an inbound shipment (STM-managed for item-level state tracking)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_inbound_shipment_item")
public class InboundShipmentItem extends AbstractJpaStateEntity {

    @Column(name = "shipment_id", length = 36, nullable = false)
    private String shipmentId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "expected_quantity", nullable = false)
    private Integer expectedQuantity;

    @Column(name = "received_quantity")
    private Integer receivedQuantity = 0;

    @Column(name = "damaged_quantity")
    private Integer damagedQuantity = 0;

    @Column(name = "condition_type", length = 50)
    private String conditionType;
}
