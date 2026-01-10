package com.handmade.ecommerce.platform.event.entity;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_event_subscription
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_event_subscription")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class EventSubscription extends BaseJpaEntity {
    
    @Column(name = "event_type", nullable = false, length = 100)
    private String eventType;
    @Column(name = "subscriber_endpoint", nullable = false, length = 255)
    private String subscriberEndpoint;
    @Column(name = "is_active")
    private Boolean isActive;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
