package com.handmade.ecommerce.inventory.model;

import jakarta.persistence.*;
import org.chenile.utils.entity.model.ChenileEntity;
import java.time.LocalDateTime;

/**
 * InventoryItem - Aggregate Root
 * Represents stock for a product SKU
 */
@Entity
@Table(name = "inventory_items", indexes = {
        @Index(name = "idx_sku", columnList = "sku"),
        @Index(name = "idx_warehouse", columnList = "warehouse_id")
})
public class InventoryItem extends ChenileEntity {

    @Id
    @Column(name = "inventory_id", length = 50)
    private String inventoryId;

    @Column(name = "sku", nullable = false, unique = true, length = 50)
    private String sku; // Product SKU reference

    @Column(name = "product_id", length = 50)
    private String productId; // Reference to Product Management

    @Column(name = "warehouse_id", length = 50)
    private String warehouseId; // Reference to warehouse/location

    @Column(name = "quantity_available")
    private Integer quantityAvailable = 0;

    @Column(name = "quantity_reserved")
    private Integer quantityReserved = 0;

    @Column(name = "quantity_damaged")
    private Integer quantityDamaged = 0;

    @Column(name = "reorder_level")
    private Integer reorderLevel; // Minimum stock level before reorder

    @Column(name = "reorder_quantity")
    private Integer reorderQuantity; // Quantity to reorder

    @Column(name = "last_restocked_at")
    private LocalDateTime lastRestockedAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // Getters and Setters

    public String getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(String inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(String warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Integer getQuantityReserved() {
        return quantityReserved;
    }

    public void setQuantityReserved(Integer quantityReserved) {
        this.quantityReserved = quantityReserved;
    }

    public Integer getQuantityDamaged() {
        return quantityDamaged;
    }

    public void setQuantityDamaged(Integer quantityDamaged) {
        this.quantityDamaged = quantityDamaged;
    }

    public Integer getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(Integer reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public Integer getReorderQuantity() {
        return reorderQuantity;
    }

    public void setReorderQuantity(Integer reorderQuantity) {
        this.reorderQuantity = reorderQuantity;
    }

    public LocalDateTime getLastRestockedAt() {
        return lastRestockedAt;
    }

    public void setLastRestockedAt(LocalDateTime lastRestockedAt) {
        this.lastRestockedAt = lastRestockedAt;
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

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Get total quantity (available + reserved)
     */
    public Integer getTotalQuantity() {
        return quantityAvailable + quantityReserved;
    }

    /**
     * Check if item is in stock
     */
    public boolean isInStock() {
        return quantityAvailable > 0;
    }

    /**
     * Check if reorder is needed
     */
    public boolean needsReorder() {
        return reorderLevel != null && getTotalQuantity() <= reorderLevel;
    }

    /**
     * Reserve stock for an order
     */
    public boolean reserveStock(int quantity) {
        if (quantityAvailable >= quantity) {
            quantityAvailable -= quantity;
            quantityReserved += quantity;
            return true;
        }
        return false;
    }

    /**
     * Release reserved stock (e.g., order cancelled)
     */
    public void releaseReservedStock(int quantity) {
        if (quantityReserved >= quantity) {
            quantityReserved -= quantity;
            quantityAvailable += quantity;
        }
    }

    /**
     * Fulfill reserved stock (e.g., order shipped)
     */
    public void fulfillReservedStock(int quantity) {
        if (quantityReserved >= quantity) {
            quantityReserved -= quantity;
        }
    }
}
