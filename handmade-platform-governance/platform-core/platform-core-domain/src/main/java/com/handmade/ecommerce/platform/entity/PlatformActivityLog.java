package com.handmade.ecommerce.platform.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_platform_activity_log
 * Upgraded to Amazon-grade with links to user and platform contexts.
 */
@Entity
@Table(name = "hm_platform_activity_log")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
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
    private LocalDateTime occurredAt;
}
