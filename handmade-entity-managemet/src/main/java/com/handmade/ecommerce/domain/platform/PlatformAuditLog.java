package com.handmade.ecommerce.domain.platform;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * Platform Audit Log - Audit trail for platform events
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_platform_audit_log", indexes = {
        @Index(name = "idx_platform_audit_platform_id", columnList = "platform_id"),
        @Index(name = "idx_platform_audit_occurred_at", columnList = "occurred_at")
})
public class PlatformAuditLog extends BaseJpaEntity {

    @Column(name = "platform_id", length = 36)
    private String platformId;

    @Column(name = "event_type", length = 100)
    private String eventType;

    @Column(name = "event_status", length = 20)
    private String eventStatus;

    @Lob
    @Column(name = "payload_json")
    private String payloadJson;

    @Column(name = "actor_id", length = 255)
    private String actorId;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "occurred_at")
    private Date occurredAt;
}
