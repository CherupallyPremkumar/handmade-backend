package com.handmade.ecommerce.platform.domain.aggregate;

import org.chenile.jpautils.entity.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Store Aggregate in Platform Management.
 * Represents a registered seller storefront in the marketplace platform.
 */
@Entity
@Table(name = "platform_store")
@Getter
@Setter
@ToString
public class Store extends BaseJpaEntity {

    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;

    @Column(name = "store_name", nullable = false, length = 100)
    private String storeName;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @Column(name = "activated_at")
    private LocalDateTime activatedAt;

    @Override
    protected String getPrefix() {
        return "STORE";
    }
}
