package com.handmade.ecommerce.compliance.domain.entity;

import org.chenile.jpautils.entity.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Compliance Snapshot for a Seller.
 * Captures the state of compliance rules at the time of activation.
 */
@Entity
@Table(name = "compliance_snapshot")
@Getter
@Setter
@ToString
public class ComplianceSnapshot extends BaseJpaEntity {

    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;

    @Column(name = "policy_id", length = 50)
    private String policyId;

    @Column(name = "snapshot_date")
    private LocalDateTime snapshotDate = LocalDateTime.now();

    @Column(name = "status", length = 20)
    private String status; // COMPLIANT, NON_COMPLIANT

    @Override
    protected String getPrefix() {
        return "COMP_SNAP";
    }
}
