package com.handmade.ecommerce.domain.logistics;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

/**
 * ShippingLabel - Generated shipping labels for shipments
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_shipping_label")
public class ShippingLabel extends BaseJpaEntity {

    @Column(name = "shipment_id", length = 36, nullable = false)
    private String shipmentId;

    @Column(name = "carrier_id", length = 36, nullable = false)
    private String carrierId;

    @Column(name = "tracking_number", length = 100, nullable = false, unique = true)
    private String trackingNumber;

    @Column(name = "label_image_url", length = 1000)
    private String labelImageUrl;

    @Column(name = "label_format", length = 20)
    private String labelFormat; // PDF, ZPL

    @Column(name = "shipping_cost", precision = 19, scale = 4)
    private BigDecimal shippingCost;

    @Column(name = "status", length = 20)
    private String status; // GENERATED, VOIDED
}
