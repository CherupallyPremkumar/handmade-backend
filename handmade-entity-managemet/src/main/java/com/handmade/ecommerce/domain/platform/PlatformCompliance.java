package com.handmade.ecommerce.domain.platform;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.util.Date;

/**
 * Platform Compliance - Compliance requirements for platform (VAT, GDPR, etc.)
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_platform_compliance")
public class PlatformCompliance extends BaseJpaEntity {

    @Column(name = "platform_id", length = 36, nullable = false)
    private String platformId;

    @Column(name = "compliance_code", length = 50, nullable = false)
    private String complianceCode; // VAT_REQUIRED, GDPR

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "applicable_to", length = 20)
    private String applicableTo; // SELLER, BUYER, ADMIN

    @Column(name = "compliance_version", length = 20)
    private String complianceVersion;

    @Column(name = "effective_from")
    private Date effectiveFrom;

    @Column(name = "effective_to")
    private Date effectiveTo;

    @Column(name = "is_mandatory", nullable = false)
    private Boolean isMandatory = true;
}
