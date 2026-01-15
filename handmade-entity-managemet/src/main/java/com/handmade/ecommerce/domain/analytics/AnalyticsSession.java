package com.handmade.ecommerce.domain.analytics;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_analytics_session")
public class AnalyticsSession extends BaseJpaEntity {

    @Column(name = "user_id", length = 36)
    private String userId;

    @Column(name = "session_token", length = 255, nullable = false, unique = true)
    private String sessionToken;

    @Column(name = "start_time", nullable = false)
    private Date startTime;

    @Column(name = "last_activity_time")
    private Date lastActivityTime;

    @Column(name = "expires_at", nullable = false)
    private Date expiresAt;

    @Column(name = "status", length = 20)
    private String status = "ACTIVE"; // ACTIVE, EXPIRED, CLOSED

    @Column(name = "device_type", length = 50)
    private String deviceType;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(name = "user_agent", length = 500)
    private String userAgent;
}
