package com.handmade.ecommerce.platform.entity;

import com.handmade.ecommerce.platform.model.UserType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_platform_compliance
 * Upgraded to Amazon-grade with applicability scope and temporal tracking.
 */
@Entity
@Table(name = "hm_platform_compliance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PlatformCompliance extends BaseJpaEntity {

    @Column(name = "platform_id", nullable = false, length = 36)
    private String platformId;

    @Column(name = "compliance_code", nullable = false, length = 50)
    private String complianceCode;

    @Column(name = "description", length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "applicable_to", length = 20)
    private UserType applicableTo;

    @Column(name = "compliance_version", length = 20)
    private String complianceVersion;

    @Column(name = "effective_from")
    private LocalDateTime effectiveFrom;

    @Column(name = "effective_to")
    private LocalDateTime effectiveTo;

    @Column(name = "is_mandatory", nullable = false)
    private Boolean isMandatory;

    // TODO: Add relationships here
}
