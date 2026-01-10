package com.handmade.ecommerce.platform.entity;

import com.handmade.ecommerce.platform.model.PolicyStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_platform_policy
 * Upgraded to Amazon-grade with LOB support and temporal versioning.
 */
@Entity
@Table(name = "hm_platform_policy")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PlatformPolicy extends BaseJpaEntity {

    @Column(name = "platform_id", nullable = false, length = 36)
    private String platformId;

    @Column(name = "policy_type", nullable = false, length = 50)
    private String policyType;

    @Lob
    @Column(name = "policy_value", nullable = false)
    private String policyValue;

    @Column(name = "policy_version", nullable = false, length = 20)
    private String policyVersion;

    @Column(name = "effective_from")
    private LocalDateTime effectiveFrom;

    @Column(name = "effective_to")
    private LocalDateTime effectiveTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "policy_status", nullable = false, length = 20)
    private PolicyStatus policyStatus;

    @Column(name = "description", length = 500)
    private String description;

    // TODO: Add relationships here
}
