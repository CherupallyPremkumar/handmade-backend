package com.handmade.ecommerce.inventory.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * CycleCount - Periodic physical inventory count for accuracy
 * Used to detect discrepancies between system and physical stock
 * Triggers adjustments when variance is found
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_cycle_count")
public class CycleCount extends AbstractJpaStateEntity {

    @Column(name = "count_type", length = 50, nullable = false)
    private String countType; // FULL, PARTIAL, ABC_ANALYSIS

    @Column(name = "sku", length = 100)
    private String sku; // Null for full warehouse count

    @Column(name = "fulfillment_node_id", length = 36, nullable = false)
    private String fulfillmentNodeId;

    @Column(name = "bin_id", length = 36)
    private String binId;

    @Column(name = "expected_quantity", precision = 19, scale = 4)
    private BigDecimal expectedQuantity;

    @Column(name = "counted_quantity", precision = 19, scale = 4)
    private BigDecimal countedQuantity;

    @Column(name = "variance", precision = 19, scale = 4)
    private BigDecimal variance;

    @Column(name = "counted_by", length = 36)
    private String countedBy;

    @Column(name = "approved_by", length = 36)
    private String approvedBy;

    @Column(name = "scheduled_date")
    private Date scheduledDate;

    @Column(name = "completed_at")
    private Date completedAt;

    @Column(name = "adjustment_id", length = 36)
    private String adjustmentId; // Link to created adjustment
}
