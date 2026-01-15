package com.handmade.ecommerce.domain.logistics;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_warehouse_zone")
public class WarehouseZone extends BaseJpaEntity {

    @Column(name = "fulfillment_node_id", length = 36, nullable = false)
    private String fulfillmentNodeId;

    @Column(name = "zone_code", length = 50, nullable = false)
    private String zoneCode;

    @Column(name = "zone_type", length = 50)
    private String zoneType; // STORAGE, PICKING, PACKING

    @Column(name = "description")
    private String description;
}
