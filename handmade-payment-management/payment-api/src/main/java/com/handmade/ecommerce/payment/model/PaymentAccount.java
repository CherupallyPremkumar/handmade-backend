package com.handmade.ecommerce.payment.model;

import com.handmade.ecommerce.payment.model.PaymentMethod;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "hm_payment_account")
public class PaymentAccount  extends BaseJpaEntity {


    @Column(nullable = false)
    private String sellerId; // FK → Seller.id

    @Column(nullable = false, length = 50)
    private String provider; // e.g. STRIPE, PAYPAL, RAZORPAY

    @Column(nullable = false, unique = true, length = 100)
    private String providerAccountId; // e.g. acct_1234

    @Column(length = 10)
    private String currency; // e.g. INR, USD

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private PaymentStatus status;


    @OneToMany(mappedBy = "paymentAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentMethod> paymentMethods;


    public enum PaymentStatus {
        PENDING_VERIFICATION,
        ACTIVE,
        DISABLED
    }
}