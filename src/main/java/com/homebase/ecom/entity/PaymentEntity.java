package com.homebase.ecom.entity;

import com.homebase.ecom.entity.OrderEntity;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
public class PaymentEntity extends AbstractJpaStateEntity {

    String orderId;

    @Column(nullable = false)
    private String transactionId;

    private String merchantTransactionId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency = "INR";



}
