package com.handmade.ecommerce.platform.governance.entity;

import com.handmade.ecommerce.platform.governance.model.GdprRequestStatus;
import com.handmade.ecommerce.platform.governance.model.GdprRequestType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_gdpr_request
 * Tracks user data privacy requests.
 */
@Entity
@Table(name = "hm_gdpr_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class GdprRequest extends AbstractJpaStateEntity {

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type", length = 50)
    private GdprRequestType requestType;

    //im removing this because chenile stm handles this
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private GdprRequestStatus status;

    @Column(name = "requested_at")
    private LocalDateTime requestedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "policy_ack_id")
    @ToString.Exclude
    private PrivacyPolicyAck policyAck;

    @Column(name = "policy_id", length = 50)
    private String policyId;
}
