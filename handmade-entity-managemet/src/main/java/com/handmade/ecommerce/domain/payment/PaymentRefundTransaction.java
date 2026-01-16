package com.handmade.ecommerce.domain.payment;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;

/**
 * PaymentRefundTransaction - Payment refund transaction
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_payment_refund_transaction")
public class PaymentRefundTransaction extends AbstractJpaStateEntity {

    @Column(name = "payment_transaction_id", length = 36, nullable = false)
    private String paymentTransactionId;

    @Column(name = "refund_amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal refundAmount;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "provider_refund_id", length = 255)
    private String providerRefundId;

    @Lob
    @Column(name = "reason")
    private String reason;

    // Status managed by STM (PENDING, COMPLETED, FAILED)
}
