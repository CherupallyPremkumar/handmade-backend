package com.handmade.ecommerce.domain.dispute;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;

/**
 * DisputeResolution - Formal resolution record for a dispute
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_dispute_resolution")
public class DisputeResolution extends AbstractJpaStateEntity {

    @Column(name = "dispute_id", length = 36, nullable = false)
    private String disputeId;

    @Column(name = "resolution_code", length = 100, nullable = false)
    private String resolutionCode; // FULL_REFUND, PARTIAL_REFUND, NO_ACTION

    @Column(name = "outcome_summary", columnDefinition = "TEXT")
    private String outcomeSummary;

    @Column(name = "refund_amount", precision = 19, scale = 4)
    private BigDecimal refundAmount;

    @Column(name = "approved_by", length = 36)
    private String approvedBy; // Support Agent ID

    @Column(name = "note_to_buyer", columnDefinition = "TEXT")
    private String noteToBuyer;

    @Column(name = "note_to_seller", columnDefinition = "TEXT")
    private String noteToSeller;
}
