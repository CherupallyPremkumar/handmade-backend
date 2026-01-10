package com.handmade.ecommerce.inventory.operations.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_inventory_health
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_inventory_health")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class InventoryHealth extends BaseJpaEntity {
    
    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "fnsku", nullable = false, length = 100)
    private String fnsku;
    @Column(name = "fulfillment_node_id", length = 36)
    private String fulfillmentNodeId;
    @Column(name = "snapshot_date", nullable = false)
    private Date snapshotDate;
    @Column(name = "qty_sellable")
    private String qtySellable;
    @Column(name = "age_0_60_days")
    private String age060Days;
    @Column(name = "age_61_90_days")
    private String age6190Days;
    @Column(name = "age_91_180_days")
    private String age91180Days;
    @Column(name = "age_181_365_days")
    private String age181365Days;
    @Column(name = "age_365_plus_days")
    private String age365PlusDays;
    @Column(name = "est_long_term_storage_fee", precision = 19, scale = 4)
    private BigDecimal estLongTermStorageFee;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
