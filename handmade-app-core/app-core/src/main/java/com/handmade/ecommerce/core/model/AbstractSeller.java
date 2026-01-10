package com.handmade.ecommerce.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Abstract base class for Seller entities
 * Provides seller ID reference for all seller-related entities
 */
@MappedSuperclass
public  abstract class AbstractSeller extends AbstractJpaStateEntity {

    /**
     * Reference to the seller ID
     */
    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    public String getSellerId() {
        return sellerId;
    }
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}