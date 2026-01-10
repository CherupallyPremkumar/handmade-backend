package com.handmade.ecommerce.order.dispute.entity;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_dispute_evidence
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_dispute_evidence")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class DisputeEvidence extends BaseJpaEntity {
    
    @Column(name = "dispute_id", nullable = false, length = 36)
    private String disputeId;
    @Column(name = "submitted_by", nullable = false, length = 255)
    private String submittedBy;
    @Column(name = "submitter_role", nullable = false, length = 50)
    private String submitterRole;
    @Column(name = "evidence_type", nullable = false, length = 100)
    private String evidenceType;
    @Column(name = "description")
    private String description;
    @Column(name = "document_url", length = 2048)
    private String documentUrl;
    @Column(name = "submitted_at", nullable = false)
    private Date submittedAt;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
