package com.handmade.ecommerce.domain.analytics;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * AnalyticsEvent - Generic analytics event log
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_analytics_event")
public class AnalyticsEvent extends BaseJpaEntity {

    @Column(name = "event_type", length = 100, nullable = false)
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

    @Column(name = "payload_json", columnDefinition = "TEXT")
    private String payloadJson;

    @Column(name = "source", length = 50)
    private String source; // WEB, MOBILE, API

    @Column(name = "url", length = 2048)
    private String url;
}
