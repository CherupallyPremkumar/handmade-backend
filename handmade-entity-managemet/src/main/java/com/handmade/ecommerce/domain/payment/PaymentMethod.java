package com.handmade.ecommerce.domain.payment;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

/**
 * PaymentMethod - Customer saved payment methods
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_payment_method")
public class PaymentMethod extends BaseJpaEntity {

    @Column(name = "customer_id", length = 36, nullable = false)
    private String customerId;

    @Column(name = "payment_type", length = 50, nullable = false)
    private String paymentType; // CARD, UPI, NETBANKING, WALLET

    @Column(name = "provider", length = 100)
    private String provider; // STRIPE, RAZORPAY, etc.

    @Column(name = "provider_payment_method_id", length = 255)
    private String providerPaymentMethodId;

    @Column(name = "card_last4", length = 4)
    private String cardLast4;

    @Column(name = "card_brand", length = 50)
    private String cardBrand;

    @Column(name = "card_exp_month")
    private Integer cardExpMonth;

    @Column(name = "card_exp_year")
    private Integer cardExpYear;

    @Lob
    @Column(name = "billing_details")
    private String billingDetails;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Column(name = "is_active")
    private Boolean isActive = true;
}
