package com.handmade.ecommerce.analytics.event.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_analytics_event
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_analytics_event")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class AnalyticsEvent extends BaseJpaEntity {
    
    @Column(name = "event_type", nullable = false, length = 100)
    private String eventType;
    @Column(name = "session_id", length = 36)
    private String sessionId;
    @Column(name = "user_id", length = 36)
    private String userId;
    @Column(name = "entity_id", length = 36)
    private String entityId;
    @Column(name = "entity_type", length = 50)
    private String entityType;
    @Column(name = "event_timestamp", nullable = false)
    private Date eventTimestamp;
    @Column(name = "payload_json")
    private String payloadJson;
    @Column(name = "source", length = 50)
    private String source;
    @Column(name = "url", length = 2048)
    private String url;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
