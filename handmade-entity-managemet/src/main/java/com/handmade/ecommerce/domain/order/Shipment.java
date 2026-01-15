package com.handmade.ecommerce.domain.order;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * Shipment - Order shipment managed by STM
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_shipment")
public class Shipment extends AbstractJpaStateEntity {

    @Column(name = "order_id", length = 36, nullable = false)
    private String orderId;

    @Column(name = "seller_id", length = 36, nullable = false)
    private String sellerId;

    @Column(name = "tracking_number", length = 100)
private String trackingNumber;

    @Column(name = "carrier", length = 100)
    private String carrier;

    @Column(name = "shipping_method", length = 50)
    private String shippingMethod;

    @Column(name = "estimated_delivery_date")
    private Date estimatedDeliveryDate;

    @Column(name = "actual_delivery_date")
    private Date actualDeliveryDate;
}
