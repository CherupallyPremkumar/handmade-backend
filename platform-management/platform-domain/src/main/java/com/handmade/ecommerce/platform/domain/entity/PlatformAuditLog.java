package com.handmade.ecommerce.platform.domain.entity;

import org.chenile.jpautils.entity.BaseJpaEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Immutable append-only log of ALL events on the Platform Aggregate.
 * Distinct from Chenile's workflow ActivityLog - this is for specific domain audit requirements.
 * PURE DOMAIN MODEL (no persistence annotations).
 */
public class PlatformAuditLog  extends BaseJpaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private String platformId;
    private String eventType; // e.g., COMMISSION_UPDATED
    private String payloadJson;
    private String actorId;
    private String ipAddress;
    private LocalDateTime occurredAt;

    protected PlatformAuditLog() {}

    public PlatformAuditLog(String platformId, String eventType, String payloadJson, String actorId, String ipAddress) {
        this.platformId = platformId;
        this.eventType = eventType;
        this.payloadJson = payloadJson;
        this.actorId = actorId;
        this.ipAddress = ipAddress;
        this.occurredAt = LocalDateTime.now();
    }
    
    public String getPlatformId() { return platformId; }
    public String getEventType() { return eventType; }
    public String getPayloadJson() { return payloadJson; }
    public String getActorId() { return actorId; }
    public String getIpAddress() { return ipAddress; }
    public LocalDateTime getOccurredAt() { return occurredAt; }
    
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
