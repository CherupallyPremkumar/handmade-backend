package com.handmade.ecommerce.domain.event;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_event_subscription")
public class EventSubscription extends BaseJpaEntity {

    @Column(name = "event_type", length = 100, nullable = false)
    private String eventType;

    @Column(name = "subscriber_endpoint", length = 255, nullable = false)
    private String subscriberEndpoint;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
