package com.handmade.ecommerce.domain.risk;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * CompliancePolicy - Manages the lifecycle of compliance policies
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_compliance_policy")
public class CompliancePolicy extends AbstractJpaStateEntity {

    @Column(name = "policy_name", length = 255, nullable = false)
    private String policyName;

    @Column(name = "policy_type", length = 50, nullable = false)
    private String policyType; // KYC, AML, GDPR

    @Column(name = "policy_code", length = 100, nullable = false)
    private String policyCode;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "enforcement_level", length = 50)
    private String enforcementLevel; // MANDATORY, RECOMMENDED

    @Column(name = "applicable_region", length = 50)
    private String applicableRegion;

    @Column(name = "policy_version", length = 50)
    private String policyVersion;

    @Column(name = "effective_date")
    private Date effectiveDate;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @Column(name = "policy_document_url", length = 500)
    private String policyDocumentUrl;

    @Column(name = "approval_required")
    private Boolean approvalRequired;

    @Column(name = "approved_by", length = 255)
    private String approvedBy;

    @Column(name = "approval_date")
    private Date approvalDate;
}
