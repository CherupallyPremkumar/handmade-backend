package com.handmade.ecommerce.domain.governance;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.util.Date;

/**
 * AuditProcess - Manages audit workflows for compliance, finance, or security
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_audit_process")
public class AuditProcess extends AbstractJpaStateEntity {

    @Column(name = "audit_type", length = 50, nullable = false)
    private String auditType; // FINANCIAL, COMPLIANCE, SECURITY

    @Column(name = "entity_type", length = 100, nullable = false)
    private String entityType; // SELLER, ORDER, TRANSACTION

    @Column(name = "entity_id", length = 36, nullable = false)
    private String entityId;

    @Column(name = "auditor_id", length = 36)
    private String auditorId;

    @Column(name = "status", length = 50)
    private String status; // Managed by STM, but kept for querying if needed

    @Column(name = "priority", length = 50)
    private String priority; // LOW, MEDIUM, HIGH, CRITICAL

    @Column(name = "scope", columnDefinition = "TEXT")
    private String scope;

    @Column(name = "findings", columnDefinition = "TEXT")
    private String findings;

    @Column(name = "risk_rating", length = 50)
    private String riskRating;

    @Column(name = "remediation_required")
    private Boolean remediationRequired;

    @Column(name = "initiated_date")
    private Date initiatedDate;

    @Column(name = "due_date")
    private Date dueDate;

    @Column(name = "completed_date")
    private Date completedDate;
}
