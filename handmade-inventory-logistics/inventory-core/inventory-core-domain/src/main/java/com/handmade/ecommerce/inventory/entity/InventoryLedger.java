package com.handmade.ecommerce.inventory.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_inventory_ledger
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_inventory_ledger")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class InventoryLedger extends BaseJpaEntity {
    
    @Column(name = "fulfillment_node_id", nullable = false, length = 36)
    private String fulfillmentNodeId;
    @Column(name = "sku", nullable = false, length = 100)
    private String sku;
    @Column(name = "quantity_change", nullable = false)
    private String quantityChange;
    @Column(name = "reason", length = 50)
    private String reason;
    @Column(name = "reference_id", length = 36)
    private String referenceId;
    @Column(name = "transaction_time", nullable = false)
    private Date transactionTime;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
