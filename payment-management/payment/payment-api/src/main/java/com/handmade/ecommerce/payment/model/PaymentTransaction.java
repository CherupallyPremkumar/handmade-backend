package com.handmade.ecommerce.payment.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_transaction")
public class PaymentTransaction extends BaseJpaEntity {

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "order_item_id")
    private String orderItemId; // optional if it's full order payout

    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    @Column(name = "buyer_id")
    private String buyerId;

    @Column(length = 50)
    private String provider; // STRIPE / RAZORPAY

    @Column(length = 100)
    private String providerTransactionId;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private TransactionStatus status; // PENDING / SUCCESS / FAILED

    @Column(length = 3)
    private String currency;

}