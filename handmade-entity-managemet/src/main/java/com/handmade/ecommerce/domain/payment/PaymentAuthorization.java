package com.handmade.ecommerce.domain.payment;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * PaymentAuthorization - Payment authorization holds
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_payment_authorization")
public class PaymentAuthorization extends AbstractJpaStateEntity {

    @Column(name = "payment_transaction_id", length = 36, nullable = false)
    private String paymentTransactionId;

    @Column(name = "authorized_amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal authorizedAmount;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "provider_authorization_id", length = 255)
    private String providerAuthorizationId;

    @Column(name = "authorization_code", length = 100)
    private String authorizationCode;

    @Column(name = "expires_at")
    private Date expiresAt;

    // Status managed by STM (AUTHORIZED, CAPTURED, EXPIRED, VOIDED)
}
