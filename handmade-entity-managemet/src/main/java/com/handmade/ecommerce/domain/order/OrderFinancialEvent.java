package com.handmade.ecommerce.domain.order;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * OrderFinancialEvent - Links orders to financial events
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_order_financial_event")
public class OrderFinancialEvent extends BaseJpaEntity {

    @Column(name = "order_id", length = 36, nullable = false)
    private String orderId;

    @Column(name = "financial_event_id", length = 36, nullable = false)
    private String financialEventId;

    @Column(name = "event_type", length = 50)
    private String eventType;
}
