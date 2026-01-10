package com.handmade.ecommerce.customer.payment.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_payment_refund_transaction
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_payment_refund_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PaymentRefundTransaction extends BaseJpaEntity {
    
    @Column(name = "payment_transaction_id", nullable = false, length = 36)
    private String paymentTransactionId;
    @Column(name = "refund_id", length = 36)
    private String refundId;
    @Column(name = "refund_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal refundAmount;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "refund_reason", length = 500)
    private String refundReason;
    @Column(name = "provider_refund_id", length = 255)
    private String providerRefundId;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "initiated_at", nullable = false)
    private Date initiatedAt;
    @Column(name = "completed_at")
    private Date completedAt;
    @Column(name = "failed_at")
    private Date failedAt;
    @Column(name = "failure_reason")
    private String failureReason;
    @Column(name = "initiated_by", length = 255)
    private String initiatedBy;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
