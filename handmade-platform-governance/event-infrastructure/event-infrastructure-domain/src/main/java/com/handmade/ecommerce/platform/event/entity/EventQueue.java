package com.handmade.ecommerce.platform.event.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_event_queue
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_event_queue")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class EventQueue extends BaseJpaEntity {
    
    @Column(name = "event_type", nullable = false, length = 100)
    private String eventType;
    @Column(name = "payload", nullable = false)
    private String payload;
    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "retry_count")
    private String retryCount;
    @Column(name = "next_retry_time")
    private Date nextRetryTime;
    @Column(name = "error_message")
    private String errorMessage;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
