package com.handmade.ecommerce.inventory.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Variant-based Inventory
 * For products with variants (Electronics, Clothes)
 * Tracks inventory by variant ID
 */
@Entity
@DiscriminatorValue("VARIANT_BASED")
@Getter
@Setter
public class VariantInventory extends Inventory {

    @Column(name = "variant_id", nullable = false)
    private Long variantId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    @Column(name = "reorder_point")
    private Integer reorderPoint;

    @Column(name = "max_stock_level")
    private Integer maxStockLevel;

    @Override
    public boolean isAvailable() {
        return getAvailableQuantity() > 0;
    }

    @Override
    public Integer getAvailableQuantity() {
        return Math.max(0, quantity - getReservedQuantity());
    }

    /**
     * Add stock
     */
    public void addStock(Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity += quantity;
    }

    /**
     * Remove stock
     */
    public void removeStock(Integer quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (this.quantity < quantity) {
            throw new IllegalStateException("Insufficient stock");
        }
        this.quantity -= quantity;
    }

    /**
     * Check if reorder is needed
     */
    public boolean needsReorder() {
        return reorderPoint != null && quantity <= reorderPoint;
    }
}
