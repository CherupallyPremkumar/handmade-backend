package com.handmade.ecommerce.domain.payment;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;

/**
 * PaymentOrder - Payment order managed by STM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_payment_order")
public class PaymentOrder extends AbstractJpaStateEntity {

    @Column(name = "order_id", length = 36, nullable = false)
    private String orderId;

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(name = "currency_code", length = 3, nullable = false)
    private String currencyCode;

    @Column(name = "provider", length = 100)
    private String provider;

    @Column(name = "provider_order_id", length = 255)
    private String providerOrderId;

    @Lob
    @Column(name = "payment_link")
    private String paymentLink;

    @Lob
    @Column(name = "callback_url")
    private String callbackUrl;
}
