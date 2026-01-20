package com.handmade.ecommerce.domain.inventory;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * InventorySnapshot - Point-in-time inventory snapshot
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_inventory_snapshot")
public class InventorySnapshot extends BaseJpaEntity {

    @Column(name = "fulfillment_node_id", length = 36, nullable = false)
    private String fulfillmentNodeId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;

    @Column(name = "reserved_quantity")
    private Integer reservedQuantity = 0;

    @Column(name = "damaged_quantity")
    private Integer damagedQuantity = 0;

    @Column(name = "snapshot_time", nullable = false)
    private Date snapshotDate;
}
