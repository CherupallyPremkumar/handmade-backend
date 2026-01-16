package com.handmade.ecommerce.analytics.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_user_behavior_signal")
public class BehaviorSignal extends BaseJpaEntity {

    @Column(name = "user_id", length = 36)
    private String userId;

    @Column(name = "session_id", length = 100)
    private String sessionId;

    @Column(name = "product_id", length = 36, nullable = false)
    private String productId;

    @Column(name = "dwell_time_seconds")
    private Integer dwellTimeSeconds;

    @Column(name = "interaction_type", length = 50)
    private String interactionType;

    @Lob
    @Column(name = "metadata", columnDefinition = "TEXT")
    private String metadata;

    @Column(name = "captured_at")
    private Date capturedAt;
}
