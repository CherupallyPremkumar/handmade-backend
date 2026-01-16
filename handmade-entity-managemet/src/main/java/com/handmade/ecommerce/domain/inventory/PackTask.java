package com.handmade.ecommerce.domain.inventory;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * PackTask - Warehouse packing task managed by STM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_pack_task")
public class PackTask extends AbstractJpaStateEntity {

    @Column(name = "pick_task_id", length = 36, nullable = false)
    private String pickTaskId;

    @Column(name = "order_id", length = 36, nullable = false)
    private String orderId;

    @Column(name = "packer_user_id", length = 36)
    private String packerUserId;

    @Column(name = "packaging_type", length = 50)
    private String packagingType;

    @Column(name = "weight_grams")
    private Integer weightGrams;

    @Column(name = "dimensions_cm", length = 50)
    private String dimensionsCm; // e.g., "30x20x10"
}
