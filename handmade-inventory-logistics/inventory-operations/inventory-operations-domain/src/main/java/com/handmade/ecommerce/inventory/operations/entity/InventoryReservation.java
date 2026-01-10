package com.handmade.ecommerce.inventory.operations.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_inventory_reservation
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_inventory_reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class InventoryReservation extends BaseJpaEntity {
    
    @Column(name = "inventory_item_id", nullable = false, length = 36)
    private String inventoryItemId;
    @Column(name = "order_id", nullable = false, length = 36)
    private String orderId;
    @Column(name = "quantity", nullable = false)
    private String quantity;
    @Column(name = "status", nullable = false, length = 20)
    private String status;
    @Column(name = "expires_at", nullable = false)
    private Date expiresAt;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
