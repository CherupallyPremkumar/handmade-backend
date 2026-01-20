package com.handmade.ecommerce.domain.inventory;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * InventoryPosition - Product location tracking in bins
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_inventory_position")
public class InventoryPosition extends BaseJpaEntity {

    @Column(name = "bin_id", length = 36, nullable = false)
    private String binId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "quantity_on_hand")
    private Integer quantity = 0;

    @Column(name = "is_primary_location")
    private Boolean isPrimaryLocation = false;
}
