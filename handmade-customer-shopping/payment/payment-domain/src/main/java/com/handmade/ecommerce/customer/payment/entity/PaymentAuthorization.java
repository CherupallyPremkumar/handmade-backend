package com.handmade.ecommerce.customer.payment.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * JPA Entity for hm_payment_authorization
 * Generated from Liquibase changelog
 * 
 * Note: Relationships (@OneToMany, @ManyToOne, @ManyToMany) must be added manually
 * after generation. This ensures proper mapping and avoids circular dependencies.
 */
@Entity
@Table(name = "hm_payment_authorization")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class PaymentAuthorization extends BaseJpaEntity {
    
    @Column(name = "payment_transaction_id", nullable = false, length = 36)
    private String paymentTransactionId;
    @Column(name = "authorization_code", nullable = false, length = 100)
    private String authorizationCode;
    @Column(name = "authorized_amount", nullable = false, precision = 19, scale = 4)
    private BigDecimal authorizedAmount;
    @Column(name = "currency_code", length = 3)
    private String currencyCode;
    @Column(name = "provider_authorization_id", length = 255)
    private String providerAuthorizationId;
    @Column(name = "authorized_at", nullable = false)
    private Date authorizedAt;
    @Column(name = "expires_at")
    private Date expiresAt;
    @Column(name = "status", nullable = false, length = 50)
    private String status;
    @Column(name = "captured_amount", precision = 19, scale = 4)
    private BigDecimal capturedAmount;
    @Column(name = "voided_at")
    private Date voidedAt;
    @Column(name = "void_reason", length = 500)
    private String voidReason;
    
    // TODO: Add relationships here
    // Example:
    // @ManyToOne
    // @JoinColumn(name = "parent_id")
    // private ParentEntity parent;
}
