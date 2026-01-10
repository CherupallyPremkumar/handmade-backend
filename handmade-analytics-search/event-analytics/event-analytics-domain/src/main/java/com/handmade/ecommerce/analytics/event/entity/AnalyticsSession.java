package com.handmade.ecommerce.analytics.event.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_analytics_session
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_analytics_session")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class AnalyticsSession extends BaseJpaEntity {
    
    @Column(name = "user_id", length = 36)
    private String userId;
    @Column(name = "session_token", nullable = false, length = 255, unique = true)
    private String sessionToken;
    @Column(name = "start_time", nullable = false)
    private Date startTime;
    @Column(name = "last_activity_time")
    private Date lastActivityTime;
    @Column(name = "expires_at", nullable = false)
    private Date expiresAt;
    @Column(name = "status", length = 20)
    private String status;
    @Column(name = "device_type", length = 50)
    private String deviceType;
    @Column(name = "ip_address", length = 45)
    private String ipAddress;
    @Column(name = "user_agent", length = 500)
    private String userAgent;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
