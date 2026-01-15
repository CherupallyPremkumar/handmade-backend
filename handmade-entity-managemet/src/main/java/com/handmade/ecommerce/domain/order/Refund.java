package com.handmade.ecommerce.domain.order;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;

/**
 * Refund - Order refund managed by STM
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_refund")
public class Refund extends AbstractJpaStateEntity {

    @Column(name = "order_id", length = 36, nullable = false)
    private String orderId;

    @Column(name = "return_request_id", length = 36)
    private String returnRequestId;

    @Column(name = "refund_amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal refundAmount;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "refund_method", length = 50)
    private String refundMethod;

    @Column(name = "reason", columnDefinition = "TEXT")
    private String reason;
}
