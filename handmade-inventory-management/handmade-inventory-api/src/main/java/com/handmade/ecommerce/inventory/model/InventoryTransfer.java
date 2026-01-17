package com.handmade.ecommerce.inventory.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * InventoryTransfer - Inter-warehouse or inter-bin stock movement
 * Tracks stock moving from one location to another
 * Handles partial shipments and loss in transit
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_inventory_transfer")
public class InventoryTransfer extends AbstractJpaStateEntity {

    @Column(name = "transfer_type", length = 50, nullable = false)
    private String transferType; // NODE_TO_NODE, BIN_TO_BIN

    @Column(name = "sku", length = 100, nullable = false)
    private String sku;

    @Column(name = "source_node_id", length = 36)
    private String sourceNodeId;

    @Column(name = "source_bin_id", length = 36)
    private String sourceBinId;

    @Column(name = "destination_node_id", length = 36)
    private String destinationNodeId;

    @Column(name = "destination_bin_id", length = 36)
    private String destinationBinId;

    @Column(name = "quantity_requested", precision = 19, scale = 4, nullable = false)
    private BigDecimal quantityRequested;

    @Column(name = "quantity_received", precision = 19, scale = 4)
    private BigDecimal quantityReceived;

    @Column(name = "initiated_by", length = 36)
    private String initiatedBy;

    @Column(name = "dispatched_at")
    private Date dispatchedAt;

    @Column(name = "received_at")
    private Date receivedAt;
}
