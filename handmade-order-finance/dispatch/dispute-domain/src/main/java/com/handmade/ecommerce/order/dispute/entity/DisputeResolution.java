package com.handmade.ecommerce.order.dispute.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_dispute_resolution
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_dispute_resolution")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class DisputeResolution extends BaseJpaEntity {
    
    @Column(name = "dispute_id", nullable = false, length = 36, unique = true)
    private String disputeId;
    @Column(name = "resolution_type", nullable = false, length = 100)
    private String resolutionType;
    @Column(name = "winner", nullable = false, length = 50)
    private String winner;
    @Column(name = "settlement_amount", precision = 19, scale = 4)
    private BigDecimal settlementAmount;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "resolution_notes")
    private String resolutionNotes;
    @Column(name = "resolved_by", length = 255)
    private String resolvedBy;
    @Column(name = "resolved_at", nullable = false)
    private Date resolvedAt;
    @Column(name = "refund_issued")
    private Boolean refundIssued;
    @Column(name = "refund_id", length = 36)
    private String refundId;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
