package com.handmade.ecommerce.domain.order;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * ShipmentItem - Items in a shipment (STM-managed for item-level tracking)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_shipment_item")
public class ShipmentItem extends AbstractJpaStateEntity {

    @Column(name = "shipment_id", length = 36, nullable = false)
    private String shipmentId;

    @Column(name = "order_line_id", length = 36, nullable = false)
    private String orderLineId;

    @Column(name = "quantity_shipped", nullable = false)
    private Integer quantityShipped;
}
