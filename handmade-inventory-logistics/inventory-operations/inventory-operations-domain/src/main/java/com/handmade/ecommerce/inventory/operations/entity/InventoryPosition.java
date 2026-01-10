package com.handmade.ecommerce.inventory.operations.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_inventory_position
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_inventory_position")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class InventoryPosition extends BaseJpaEntity {
    
    @Column(name = "warehouse_id", nullable = false, length = 36)
    private String warehouseId;
    @Column(name = "product_id", nullable = false, length = 36)
    private String productId;
    @Column(name = "quantity_on_hand")
    private String quantityOnHand;
    @Column(name = "quantity_reserved")
    private String quantityReserved;
    @Column(name = "quantity_available")
    private String quantityAvailable;
    @Column(name = "last_sync_at")
    private Date lastSyncAt;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
