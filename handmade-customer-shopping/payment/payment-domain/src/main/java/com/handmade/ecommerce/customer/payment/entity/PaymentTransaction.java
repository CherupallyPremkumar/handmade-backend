package com.handmade.ecommerce.customer.payment.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_payment_transaction
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_payment_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PaymentTransaction extends BaseJpaEntity {
    
    @Column(name = "order_id", nullable = false, length = 36)
    private String orderId;
    @Column(name = "customer_id", nullable = false, length = 36)
    private String customerId;
    @Column(name = "payment_method_id", length = 36)
    private String paymentMethodId;
    @Column(name = "transaction_type", nullable = false, length = 50)
    private String transactionType;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal amount;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "payment_provider", length = 100)
    private String paymentProvider;
    @Column(name = "provider_transaction_id", length = 255)
    private String providerTransactionId;
    @Column(name = "provider_response")
    private String providerResponse;
    @Column(name = "authorization_code", length = 100)
    private String authorizationCode;
    @Column(name = "card_last_four", length = 4)
    private String cardLastFour;
    @Column(name = "card_brand", length = 50)
    private String cardBrand;
    @Column(name = "billing_address_id", length = 36)
    private String billingAddressId;
    @Column(name = "initiated_at")
    private Date initiatedAt;
    @Column(name = "completed_at")
    private Date completedAt;
    @Column(name = "failed_at")
    private Date failedAt;
    @Column(name = "failure_reason")
    private String failureReason;
    @Column(name = "risk_score", precision = 5, scale = 2)
    private BigDecimal riskScore;
    @Column(name = "is_disputed")
    private Boolean isDisputed;
    @Column(name = "dispute_id", length = 36)
    private String disputeId;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
