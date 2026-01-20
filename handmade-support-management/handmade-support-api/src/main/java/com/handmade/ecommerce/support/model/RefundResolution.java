package com.handmade.ecommerce.support.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * RefundResolution - Tracks decisions made regarding refund requests
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_refund_resolution")
public class RefundResolution extends BaseJpaEntity {

    @Column(name = "claim_id", length = 50, nullable = false, unique = true)
    private String claimId;

    @Column(name = "order_id", length = 36, nullable = false)
    private String orderId;

    @Column(name = "claim_reason", length = 100, nullable = false)
    private String claimReason;

    @Column(name = "claim_status", length = 50, nullable = false)
    private String claimStatus;

    @Lob
    @Column(name = "seller_response")
    private String sellerResponse;

    @Column(name = "resolution_decision", length = 50)
    private String resolutionDecision;

    @Column(name = "refund_amount", precision = 19, scale = 2)
    private BigDecimal refundAmount;

    @Column(name = "currency", length = 3)
    private String currency;

    @Column(name = "resolved_at")
    private Date resolvedAt;
}
