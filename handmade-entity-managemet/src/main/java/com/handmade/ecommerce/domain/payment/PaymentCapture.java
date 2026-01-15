package com.handmade.ecommerce.domain.payment;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;

/**
 * PaymentCapture - Payment capture from authorization
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_payment_capture")
public class PaymentCapture extends AbstractJpaStateEntity {

    @Column(name = "authorization_id", length = 36, nullable = false)
    private String authorizationId;

    @Column(name = "captured_amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal capturedAmount;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "provider_capture_id", length = 255)
    private String providerCaptureId;

    // Status managed by STM (SUCCESS, FAILED, PENDING)
}
