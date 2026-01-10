package com.handmade.ecommerce.platform.entity;

import com.handmade.ecommerce.platform.model.EventStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_platform_audit_log
 * Upgraded to Amazon-grade with LocalDateTime and event status tracking.
 */
@Entity
@Table(name = "hm_platform_audit_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PlatformAuditLog extends BaseJpaEntity {

    @Column(name = "platform_id", length = 36)
    private String platformId;

    @Column(name = "event_type", length = 100)
    private String eventType;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_status", length = 20)
    private EventStatus eventStatus;

    @Lob
    @Column(name = "payload_json")
    private String payloadJson;

    @Column(name = "actor_id", length = 255)
    private String actorId;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "occurred_at")
    private LocalDateTime occurredAt;
}
