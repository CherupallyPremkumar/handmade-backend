package com.handmade.ecommerce.inventory.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * FulfillmentBin - Storage bin locations in warehouse
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_fulfillment_bin")
public class FulfillmentBin extends BaseJpaEntity {

    @Column(name = "fulfillment_node_id", length = 36, nullable = false)
    private String fulfillmentNodeId;

    @Column(name = "bin_code", length = 50, nullable = false)
    private String binCode;

    @Column(name = "zone", length = 50)
    private String zone;

    @Column(name = "aisle", length = 20)
    private String aisle;

    @Column(name = "rack", length = 20)
    private String rack;

    @Column(name = "shelf", length = 20)
    private String shelf;

    @Column(name = "bin_type", length = 50)
    private String binType; // STORAGE, PICKING, PACKING, STAGING

    @Column(name = "is_active")
    private Boolean isActive = true;
}
