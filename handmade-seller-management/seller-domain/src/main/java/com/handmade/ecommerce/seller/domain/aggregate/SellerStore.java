package com.handmade.ecommerce.seller.domain.aggregate;

import org.chenile.jpautils.entity.BaseJpaEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * SellerStore Aggregate Root
 * Represents a seller's storefront in the marketplace.
 * Each seller can have one store where they list and sell their products.
 * 
 * This entity belongs to the Seller bounded context, not Platform.
 * Platform governs the marketplace, but stores are seller-specific.
 */
@Entity
@Table(name = "seller_store")
@Getter
@Setter
@ToString
public class SellerStore extends BaseJpaEntity {

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
