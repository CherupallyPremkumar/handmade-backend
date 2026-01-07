package com.handmade.ecommerce.inventory.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Tracks individual stock movement events.
 */
@Entity
@Table(name = "hm_inventory_transaction")
@Getter
@Setter
public class InventoryTransaction implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "inventory_id", nullable = false, length = 36)
    private String inventoryId;

    @Column(name = "transaction_type", nullable = false, length = 30)
    private String transactionType; // INBOUND, ADJUSTMENT, SALE, RETURN, RESERVATION

    @Column(name = "quantity_change", nullable = false)
    private int quantityChange;

    @Column(name = "reference_id", length = 36)
    private String referenceId; // e.g., OrderId or AdjustmentId

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "remarks")
    private String remarks;
}
