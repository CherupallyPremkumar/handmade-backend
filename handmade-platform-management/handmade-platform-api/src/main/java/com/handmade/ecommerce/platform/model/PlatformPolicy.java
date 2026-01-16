package com.handmade.ecommerce.platform.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * Platform Policy - Policies applicable to a platform (Return Window,
 * Cancellation SLA, etc.)
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_platform_policy")
public class PlatformPolicy extends BaseJpaEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "platform_id", nullable = false)
    private Platform platform;

    @Column(name = "policy_type", length = 50, nullable = false)
    private String policyType; // RETURN_WINDOW, CANCELLATION_SLA

    @Lob
    @Column(name = "policy_value", nullable = false)
    private String policyValue;

    @Column(name = "policy_version", length = 20, nullable = false)
    private String policyVersion = "1.0";

    @Column(name = "effective_from")
    private Date effectiveFrom;

    @Column(name = "effective_to")
    private Date effectiveTo;

    @Column(name = "policy_status", length = 20, nullable = false)
    private String policyStatus = "ACTIVE";

    @Column(name = "description", length = 500)
    private String description;
}
