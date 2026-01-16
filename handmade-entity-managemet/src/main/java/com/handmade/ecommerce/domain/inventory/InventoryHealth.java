package com.handmade.ecommerce.domain.inventory;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

/**
 * InventoryHealth - Inventory health metrics
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_inventory_health")
public class InventoryHealth extends BaseJpaEntity {

    @Column(name = "fulfillment_node_id", length = 36, nullable = false)
    private String fulfillmentNodeId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "qty_sellable")
    private Integer sellableQuantity = 0;

    @Column(name = "unsellable_quantity")
    private Integer unsellableQuantity = 0;

    @Column(name = "reserved_quantity")
    private Integer reservedQuantity = 0;

    @Column(name = "turnover_rate", precision = 5, scale = 2)
    private BigDecimal turnoverRate;

    @Column(name = "days_of_supply")
    private Integer daysOfSupply;

    @Column(name = "health_status", length = 50)
    private String healthStatus; // HEALTHY, LOW_STOCK, OVERSTOCK, DEAD_STOCK
}
