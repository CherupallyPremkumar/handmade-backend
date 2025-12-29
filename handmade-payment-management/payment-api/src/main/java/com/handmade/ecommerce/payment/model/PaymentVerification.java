package com.handmade.ecommerce.payment.model;

import com.handmade.ecommerce.payment.model.PaymentMethod;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "hm_payment_verification")

public class PaymentVerification extends BaseJpaEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private VerificationStatus verificationStatus;

    @Column(columnDefinition = "TEXT")
    private String message;

    private LocalDateTime verifiedAt;

    public enum VerificationStatus {
        PENDING,
        SUCCESS,
        FAILED
    }
}