package com.handmade.ecommerce.domain.platform;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * Platform Activity Log - Tracks platform-level activities and workflow events
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_platform_activity_log")
public class PlatformActivityLog extends BaseJpaEntity {

    @Column(name = "platform_id", length = 36)
    private String platformId;

    @Column(name = "user_id", length = 36)
    private String userId;

    @Column(name = "policy_id", length = 36)
    private String policyId;

    @Column(name = "activity_name", length = 255)
    private String activityName;

    @Column(name = "activity_success")
    private Boolean activitySuccess;

    @Column(name = "activity_comment", columnDefinition = "TEXT")
    private String activityComment;

    @Column(name = "occurred_at")
    private Date occurredAt;
}
