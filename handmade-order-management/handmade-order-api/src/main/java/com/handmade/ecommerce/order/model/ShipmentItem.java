package com.handmade.ecommerce.order.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * ShipmentItem - Items in a shipment
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_shipment_item")
public class ShipmentItem extends BaseJpaEntity {

    @Column(name = "shipment_id", length = 36, nullable = false)
    private String shipmentId;

    @Column(name = "order_line_id", length = 36, nullable = false)
    private String orderLineId;

    @Column(name = "quantity_shipped", nullable = false)
    private Integer quantityShipped;
}
