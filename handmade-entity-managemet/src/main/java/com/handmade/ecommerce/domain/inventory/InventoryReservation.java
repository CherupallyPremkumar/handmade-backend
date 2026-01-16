package com.handmade.ecommerce.domain.inventory;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * InventoryReservation - Inventory reservations for orders (STM-managed:
 * CREATED → ALLOCATED → CONSUMED)
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_inventory_reservation")
public class InventoryReservation extends AbstractJpaStateEntity {

    @Column(name = "fulfillment_node_id", length = 36, nullable = false)
    private String fulfillmentNodeId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "order_id", length = 36, nullable = false)
    private String orderId;

    @Column(name = "quantity", nullable = false)
    private Integer quantityReserved;

    @Column(name = "reservation_date", nullable = false)
    private Date reservationDate;

    @Column(name = "expires_at")
    private Date expirationDate;
}
