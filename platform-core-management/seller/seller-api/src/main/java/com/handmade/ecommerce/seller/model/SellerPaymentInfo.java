package com.handmade.ecommerce.seller.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hm_seller_payment_info")
public class SellerPaymentInfo extends BaseJpaEntity {

    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    @Column(name = "preferred_method", length = 50)
    private String preferredMethod;

    @Column(length = 10)
    private String currency;

    @Column(name = "is_default_active")
    private Boolean isDefaultActive = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @OneToMany(mappedBy = "paymentInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentMethod> paymentMethods = new ArrayList<>();
}