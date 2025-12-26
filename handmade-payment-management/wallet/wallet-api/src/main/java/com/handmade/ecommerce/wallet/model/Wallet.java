package com.handmade.ecommerce.wallet.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.chenile.jpautils.entity.BaseJpaEntity;


@Getter
@Setter
@Entity
@Table(name = "hm_wallet", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_user_type", columnList = "user_type"),
        @Index(name = "idx_status", columnList = "status")
})
public class Wallet extends BaseJpaEntity  {
    /**
     * User ID (seller_id or buyer_id)
     */
    @Column(name = "user_id", nullable = false, length = 100)
    private String userId;

    /**
     * User type: SELLER, BUYER, PLATFORM
     */
    @Column(name = "user_type", nullable = false, length = 20)
    private String userType;

    /**
     * Current balance
     */
    @Column(name = "balance", nullable = false, precision = 19, scale = 4)
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * Currency code (USD, INR, etc.)
     */
    @Column(name = "currency", nullable = false, length = 3)
    private String currency;

    /**
     * Wallet status: ACTIVE, FROZEN, CLOSED
     */
    @Column(name = "status", nullable = false, length = 20)
    private String status = "ACTIVE";
}
