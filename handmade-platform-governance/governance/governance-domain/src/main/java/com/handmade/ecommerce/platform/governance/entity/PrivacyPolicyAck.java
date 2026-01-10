package com.handmade.ecommerce.platform.governance.entity;

import com.handmade.ecommerce.platform.governance.model.AckStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_privacy_policy_ack
 * Manages user acceptance of privacy policies and terms.
 */
@Entity
@Table(name = "hm_privacy_policy_ack")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PrivacyPolicyAck extends BaseJpaEntity {

    @Column(name = "user_id", nullable = false, length = 36)
    private String userId;

    @Column(name = "policy_id", length = 50)
    private String policyId;

    @Column(name = "version_accepted", length = 50)
    private String versionAccepted;

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    @Column(name = "terms_type", length = 50)
    private String termsType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private AckStatus status;

    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
