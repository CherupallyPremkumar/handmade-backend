package com.handmade.ecommerce.domain.order;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * ReturnRequest - Product return request managed by STM
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_return_request")
public class ReturnRequest extends AbstractJpaStateEntity {

    @Column(name = "order_id", length = 36, nullable = false)
    private String orderId;

    @Column(name = "order_line_id", length = 36, nullable = false)
    private String orderLineId;

    @Column(name = "quantity_requested", nullable = false)
    private Integer quantityRequested;

    @Column(name = "reason", length = 50)
    private String reason;

    @Column(name = "customer_notes", columnDefinition = "TEXT")
    private String customerNotes;

    @Column(name = "seller_notes", columnDefinition = "TEXT")
    private String sellerNotes;
}
