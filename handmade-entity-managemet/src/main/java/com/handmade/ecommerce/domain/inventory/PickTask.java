package com.handmade.ecommerce.domain.inventory;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * PickTask - Warehouse picking task managed by STM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_pick_task")
public class PickTask extends AbstractJpaStateEntity {

    @Column(name = "order_id", length = 36, nullable = false)
    private String orderId;

    @Column(name = "fulfillment_node_id", length = 36, nullable = false)
    private String fulfillmentNodeId;

    @Column(name = "picker_user_id", length = 36)
    private String pickerUserId;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "total_items")
    private Integer totalItems;

    @Column(name = "picked_items")
    private Integer pickedItems = 0;
}
