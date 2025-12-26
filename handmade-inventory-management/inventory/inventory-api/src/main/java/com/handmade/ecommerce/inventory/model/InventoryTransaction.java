package com.handmade.ecommerce.inventory.model;

import jakarta.persistence.Entity;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.time.LocalDateTime;
@Entity
public class InventoryTransaction extends BaseJpaEntity {
    private Long transactionId;       // PK: Unique identifier for the movement
    private Long variantId;           // FK: The item that moved
    private Long fromWarehouseId;     // FK: Source location (nullable for receipts)
    private Long toWarehouseId;       // FK: Destination location (nullable for shipments)
    private int quantityChange;       // Amount added (positive) or removed (negative)
    private String transactionType;   // e.g., "Receipt", "Sale", "Transfer", "Adjustment"
    private String referenceDocument; // Link to source document (Order ID, Count ID)
    private LocalDateTime timestamp;  // When the transaction occurred
}
