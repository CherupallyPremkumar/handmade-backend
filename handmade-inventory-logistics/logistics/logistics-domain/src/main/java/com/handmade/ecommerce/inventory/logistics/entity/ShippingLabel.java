package com.handmade.ecommerce.inventory.logistics.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_shipping_label
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_shipping_label")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ShippingLabel extends BaseJpaEntity {
    
    @Column(name = "shipment_id", nullable = false, length = 36)
    private String shipmentId;
    @Column(name = "carrier_id", nullable = false, length = 36)
    private String carrierId;
    @Column(name = "tracking_number", nullable = false, length = 100, unique = true)
    private String trackingNumber;
    @Column(name = "label_image_url", length = 1000)
    private String labelImageUrl;
    @Column(name = "label_format", length = 20)
    private String labelFormat;
    @Column(name = "shipping_cost", precision = 19, scale = 4)
    private BigDecimal shippingCost;
    @Column(name = "status", length = 20)
    private String status;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
