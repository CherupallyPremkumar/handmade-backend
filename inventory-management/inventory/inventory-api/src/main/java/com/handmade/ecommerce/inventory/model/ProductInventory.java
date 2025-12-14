package com.handmade.ecommerce.inventory.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Product-based Inventory
 * For perishable products (Food)
 * Tracks inventory at product level with expiry dates
 */
@Entity
@DiscriminatorValue("PRODUCT_BASED")
@Getter
@Setter
public class ProductInventory extends Inventory {

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "batch_number", length = 100)
    private String batchNumber;

    @Column(name = "manufacturing_date")
    private LocalDate manufacturingDate;

    public boolean isAvailable() {
        return !isExpired() && getAvailableQuantity() > 0;
    }

    public Integer getAvailableQuantity() {
        if (isExpired()) {
            return 0;
        }
        return Math.max(0, quantity - getReservedQuantity());
    }

    /**
     * Check if product is expired
     */
    public boolean isExpired() {
        if (expiryDate == null) {
            return false;
        }
        return expiryDate.isBefore(LocalDate.now());
    }

    /**
     * Get days until expiry
     */
    public Long getDaysUntilExpiry() {
        if (expiryDate == null) {
            return null;
        }
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
    }

    /**
     * Check if expiring soon (within 7 days)
     */
    public boolean isExpiringSoon() {
        Long days = getDaysUntilExpiry();
        return days != null && days >= 0 && days <= 7;
    }
}
