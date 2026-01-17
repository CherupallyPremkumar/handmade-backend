package com.handmade.ecommerce.inventory.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * InventoryAdjustment - Manual or system corrections to inventory
 * Used for cycle count mismatches, damage, theft, shrinkage
 * Requires approval workflow for audit compliance
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_inventory_adjustment")
public class InventoryAdjustment extends AbstractJpaStateEntity {

    @Column(name = "adjustment_type", length = 50, nullable = false)
    private String adjustmentType; // DAMAGE, THEFT, CYCLE_COUNT, SYSTEM_CORRECTION

    @Column(name = "sku", length = 100, nullable = false)
    private String sku;

    @Column(name = "fulfillment_node_id", length = 36)
    private String fulfillmentNodeId;

    @Column(name = "bin_id", length = 36)
    private String binId;

    @Column(name = "quantity_change", precision = 19, scale = 4, nullable = false)
    private BigDecimal quantityChange; // Can be negative

    @Column(name = "reason", length = 500)
    private String reason;

    @Column(name = "requested_by", length = 36)
    private String requestedBy;

    @Column(name = "approved_by", length = 36)
    private String approvedBy;

    @Column(name = "applied_at")
    private Date appliedAt;
}
