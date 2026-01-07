package com.handmade.ecommerce.inventory.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import java.io.Serializable;

/**
 * Inventory Aggregate Root
 * Tracks stock for a specific Variant and Seller (Offer level).
 */
@Entity
@Table(name = "hm_inventory", uniqueConstraints = {
        @UniqueConstraint(name = "uk_inventory_variant_seller_location", columnNames = { "variant_id", "seller_id",
                "location_id" })
})
@Getter
@Setter
public class Inventory extends AbstractJpaStateEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "variant_id", nullable = false, length = 36)
    private String variantId;

    @Column(name = "seller_id", nullable = false, length = 36)
    private String sellerId;

    @Column(name = "quantity_available", nullable = false)
    private int quantityAvailable = 0;

    @Column(name = "quantity_reserved", nullable = false)
    private int quantityReserved = 0;

    @Column(name = "is_back_order_allowed")
    private boolean backOrderAllowed = false;

    @Column(name = "location_id", length = 36)
    private String locationId;

    /**
     * Helper to get net available stock
     */
    public int getNetAvailable() {
        return quantityAvailable - quantityReserved;
    }
}
