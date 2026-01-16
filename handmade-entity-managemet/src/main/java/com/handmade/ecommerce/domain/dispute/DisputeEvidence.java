package com.handmade.ecommerce.domain.dispute;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * DisputeEvidence - Evidence files uploaded for a dispute
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_dispute_evidence")
public class DisputeEvidence extends BaseJpaEntity {

    @Column(name = "dispute_id", length = 36, nullable = false)
    private String disputeId;

    @Column(name = "submitted_by", length = 255, nullable = false)
    private String submittedBy;

    @Column(name = "submitter_role", length = 50, nullable = false)
    private String submitterRole; // CUSTOMER, SELLER, SUPPORT

    @Column(name = "document_url", length = 2048, nullable = false)
    private String documentUrl;

    @Column(name = "evidence_type", length = 100, nullable = false)
    private String evidenceType; // IMAGE, PDF, TEXT

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
