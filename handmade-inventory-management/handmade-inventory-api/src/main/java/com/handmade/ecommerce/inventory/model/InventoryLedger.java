package com.handmade.ecommerce.inventory.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * InventoryLedger - Inventory transaction ledger
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_inventory_ledger")
public class InventoryLedger extends BaseJpaEntity {

    @Column(name = "fulfillment_node_id", length = 36, nullable = false)
    private String fulfillmentNodeId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "reason", length = 50, nullable = false)
    private String transactionType; // RECEIVE, SHIP, ADJUST, DAMAGE, RETURN

    @Column(name = "quantity_change", nullable = false)
    private Integer quantityChange;

    @Column(name = "quantity_after")
    private Integer quantityAfter;

    @Column(name = "reference_type", length = 50)
    private String referenceType;

    @Column(name = "reference_id", length = 100)
    private String referenceId;

    @Lob
    @Column(name = "notes")
    private String notes;

    @Column(name = "transaction_time", nullable = false)
    private Date transactionDate;
}
