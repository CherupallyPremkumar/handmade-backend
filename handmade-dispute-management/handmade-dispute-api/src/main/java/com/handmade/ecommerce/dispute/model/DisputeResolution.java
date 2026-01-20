package com.handmade.ecommerce.dispute.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;

/**
 * DisputeResolution - Formal resolution record for a dispute
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_dispute_resolution")
public class DisputeResolution extends AbstractJpaStateEntity {

    @Column(name = "dispute_id", length = 36, nullable = false, unique = true)
    private String disputeId;

    @Column(name = "resolution_type", length = 100, nullable = false)
    private String resolutionType; // FULL_REFUND, PARTIAL_REFUND, NO_ACTION

    @Column(name = "winner", length = 50, nullable = false)
    private String winner; // BUYER, SELLER

    @Column(name = "settlement_amount", precision = 19, scale = 4)
    private BigDecimal settlementAmount;

    @Column(name = "currency_code", length = 3)
    private String currencyCode = "USD";

    @Lob
    @Column(name = "resolution_notes", columnDefinition = "TEXT")
    private String resolutionNotes;

    @Column(name = "resolved_by", length = 255)
    private String resolvedBy;

    @Column(name = "resolved_at", nullable = false)
    private java.util.Date resolvedAt;

    @Column(name = "refund_issued")
    private Boolean refundIssued = false;

    @Column(name = "refund_id", length = 36)
    private String refundId;
}
