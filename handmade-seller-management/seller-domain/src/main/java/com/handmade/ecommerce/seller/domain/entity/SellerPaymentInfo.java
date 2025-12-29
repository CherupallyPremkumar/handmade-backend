package com.handmade.ecommerce.seller.domain.entity;

import com.handmade.ecommerce.seller.domain.aggregate.Seller;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hm_seller_payment_info")
public class SellerPaymentInfo extends BaseJpaEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", insertable = false, updatable = false)
    private Seller seller;

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
    private List<PaymentMethodd> paymentMethods = new ArrayList<>();

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getPreferredMethod() {
        return preferredMethod;
    }

    public void setPreferredMethod(String preferredMethod) {
        this.preferredMethod = preferredMethod;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getDefaultActive() {
        return isDefaultActive;
    }

    public void setDefaultActive(Boolean defaultActive) {
        isDefaultActive = defaultActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<PaymentMethodd> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethodd> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}