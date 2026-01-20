package com.handmade.ecommerce.order.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

/**
 * ReturnRequest - Product return request managed by STM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_return_request")
public class ReturnRequest extends AbstractJpaStateEntity {

    @Column(name = "order_id", length = 36, nullable = false)
    private String orderId;

    @Column(name = "customer_id", length = 36)
    private String customerId;

    @Column(name = "return_reason", length = 50)
    private String returnReason;

    @Column(name = "customer_comments", length = 255)
    private String customerComments;
}
