package com.handmade.ecommerce.order.dispute.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_dispute
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_dispute")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class Dispute extends BaseJpaEntity {
    
    @Column(name = "dispute_number", nullable = false, length = 100, unique = true)
    private String disputeNumber;
    @Column(name = "order_id", nullable = false, length = 36)
    private String orderId;
    @Column(name = "payment_transaction_id", length = 36)
    private String paymentTransactionId;
    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;
    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;
    @Column(name = "dispute_type", nullable = false, length = 100)
    private String disputeType;
    @Column(name = "dispute_reason", nullable = false, length = 500)
    private String disputeReason;
    @Column(name = "dispute_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal disputeAmount;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "provider_dispute_id", length = 255)
    private String providerDisputeId;
    @Column(name = "opened_at", nullable = false)
    private Date openedAt;
    @Column(name = "respond_by_date")
    private Date respondByDate;
    @Column(name = "resolved_at")
    private Date resolvedAt;
    @Column(name = "resolution_type", length = 100)
    private String resolutionType;
    @Column(name = "resolved_amount", precision = 19, scale = 4)
    private BigDecimal resolvedAmount;
    @Column(name = "winner", length = 50)
    private String winner;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
