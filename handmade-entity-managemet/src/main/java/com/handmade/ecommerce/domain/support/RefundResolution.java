package com.handmade.ecommerce.domain.support;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;

/**
 * RefundResolution - Tracks decisions made regarding refund requests
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_refund_resolution")
public class RefundResolution extends BaseJpaEntity {

    @Column(name = "refund_request_id", length = 36, nullable = false)
    private String refundRequestId;

    @Column(name = "resolution_status", length = 50, nullable = false)
    private String resolutionStatus; // APPROVED, REJECTED, MANUAL_REVIEW

    @Column(name = "approved_amount", precision = 19, scale = 4)
    private BigDecimal approvedAmount;

    @Column(name = "rejection_reason", length = 500)
    private String rejectionReason;

    @Column(name = "processed_by", length = 36)
    private String processedBy; // System or Agent ID
}
