package com.handmade.ecommerce.inventory.logistics.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_warehouse_zone
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_warehouse_zone")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class WarehouseZone extends BaseJpaEntity {
    
    @Column(name = "warehouse_id", nullable = false, length = 36)
    private String warehouseId;
    @Column(name = "zone_code", nullable = false, length = 20)
    private String zoneCode;
    @Column(name = "zone_type", length = 50)
    private String zoneType;
    @Column(name = "capacity_volume_cbm", precision = 19, scale = 4)
    private BigDecimal capacityVolumeCbm;
    @Column(name = "is_active")
    private Boolean isActive;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
