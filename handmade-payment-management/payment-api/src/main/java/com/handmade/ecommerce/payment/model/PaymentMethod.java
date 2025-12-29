package com.handmade.ecommerce.payment.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "hm_payment_method")
public class PaymentMethod extends BaseJpaEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_account_id", nullable = false)
    private PaymentAccount paymentAccount;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private PaymentType type; // e.g. BANK, CARD, UPI

    @Column(length = 100)
    private String externalMethodId; // from provider, e.g. Razorpay method id

    @Column(length = 4)
    private String last4;

    @Column(length = 100)
    private String displayName;

    private boolean isPrimary;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private PaymentMethodStatus status;

    @OneToMany(mappedBy = "paymentMethod", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentVerification> verifications;

    public enum PaymentType {
        BANK, CARD, UPI, PAYPAL, STRIPE_WALLET
    }

    public enum PaymentMethodStatus {
        PENDING_VERIFICATION,
        ACTIVE,
        INACTIVE
    }
}