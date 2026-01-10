package com.handmade.ecommerce.support.casemanagement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_refund_resolution
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_refund_resolution")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class RefundResolution extends BaseJpaEntity {
    
    @Column(name = "claim_id", nullable = false, length = 50, unique = true)
    private String claimId;
    @Column(name = "order_id", nullable = false, length = 36)
    private String orderId;
    @Column(name = "claim_reason", nullable = false, length = 100)
    private String claimReason;
    @Column(name = "claim_status", nullable = false, length = 50)
    private String claimStatus;
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
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
