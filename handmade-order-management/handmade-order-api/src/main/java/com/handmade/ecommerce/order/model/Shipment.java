package com.handmade.ecommerce.order.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * Shipment - Order shipment managed by STM
 */
@Entity(name = "OrderShipment")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_shipment")
public class Shipment extends AbstractJpaStateEntity {

    @Column(name = "order_id", length = 36, nullable = false)
    private String orderId;

    @Column(name = "seller_id", length = 36)
    private String sellerId;

    @Column(name = "fulfillment_node_id", length = 36)
    private String fulfillmentNodeId;

    @Column(name = "tracking_number", length = 100)
    private String trackingNumber;

    @Column(name = "carrier", length = 50)
    private String carrier;

    @Column(name = "shipping_method", length = 50)
    private String shippingMethod;
}
