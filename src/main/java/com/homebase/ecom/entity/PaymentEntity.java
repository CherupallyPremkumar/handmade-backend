package com.homebase.ecom.entity;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.math.BigDecimal;

@Entity
@Table(name = "hm_payment")
public class PaymentEntity extends MultiTenantStateEntity{

    String orderId;

    @Column(nullable = false)
    private String transactionId;

    private String merchantTransactionId;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String currency = "INR";



}
