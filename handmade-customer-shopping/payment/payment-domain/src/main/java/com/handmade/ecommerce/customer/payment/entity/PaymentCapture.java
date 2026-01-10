package com.handmade.ecommerce.customer.payment.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_payment_capture
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_payment_capture")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PaymentCapture extends BaseJpaEntity {
    
    @Column(name = "payment_authorization_id", nullable = false, length = 36)
    private String paymentAuthorizationId;
    @Column(name = "capture_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal captureAmount;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "provider_capture_id", length = 255)
    private String providerCaptureId;
    @Column(name = "captured_at", nullable = false)
    private Date capturedAt;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "settlement_batch_id", length = 36)
    private String settlementBatchId;
    @Column(name = "settled_at")
    private Date settledAt;
    @Column(name = "failure_reason")
    private String failureReason;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
