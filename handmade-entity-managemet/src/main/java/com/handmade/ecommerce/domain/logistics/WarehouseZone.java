package com.handmade.ecommerce.domain.logistics;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_warehouse_zone")
public class WarehouseZone extends BaseJpaEntity {

    @Column(name = "warehouse_id", length = 36, nullable = false)
    private String warehouseId;

    @Column(name = "zone_code", length = 20, nullable = false)
    private String zoneCode;

    @Column(name = "zone_type", length = 50)
    private String zoneType; // STORAGE, PICKING, PACKING

    @Column(name = "capacity_volume_cbm", precision = 19, scale = 4)
    private BigDecimal capacityVolumeCbm;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
