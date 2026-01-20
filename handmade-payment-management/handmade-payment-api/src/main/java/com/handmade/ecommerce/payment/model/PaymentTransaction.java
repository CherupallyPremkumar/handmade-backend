package com.handmade.ecommerce.payment.model;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import java.math.BigDecimal;

/**
 * PaymentTransaction - Payment transaction managed by STM
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_payment_transaction")
public class PaymentTransaction extends AbstractJpaStateEntity {

    @Column(name = "order_id", length = 36)
    private String orderId;

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "payment_method_id", length = 36)
    private String paymentMethodId;

    @Column(name = "amount", precision = 19, scale = 4, nullable = false)
    private BigDecimal amount;

    @Column(name = "currency_code", length = 3, nullable = false)
    private String currencyCode;

    @Column(name = "provider", length = 100)
    private String provider;

    @Column(name = "provider_transaction_id", length = 255)
    private String providerTransactionId;

    @Column(name = "transaction_type", length = 50)
    private String transactionType; // PAYMENT, REFUND

    @Lob
    @Column(name = "payment_gateway_response")
    private String paymentGatewayResponse;
}
