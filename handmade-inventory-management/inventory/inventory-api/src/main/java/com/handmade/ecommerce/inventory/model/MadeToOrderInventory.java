package com.handmade.ecommerce.inventory.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Made-to-Order Inventory
 * For handmade/custom products
 * No stock tracking, only capacity and lead time
 */
@Entity
@DiscriminatorValue("MADE_TO_ORDER")
@Getter
@Setter
public class MadeToOrderInventory extends Inventory {

    @Column(name = "lead_time_days", nullable = false)
    private Integer leadTimeDays;

    @Column(name = "max_concurrent_orders")
    private Integer maxConcurrentOrders;

    @Column(name = "current_orders", nullable = false)
    private Integer currentOrders = 0;


    public boolean isAvailable() {
        if (maxConcurrentOrders == null) {
            return true; // Unlimited capacity
        }
        return currentOrders < maxConcurrentOrders;
    }

    public Integer getAvailableQuantity() {
        if (maxConcurrentOrders == null) {
            return Integer.MAX_VALUE; // Infinite
        }
        return Math.max(0, maxConcurrentOrders - currentOrders);
    }

    /**
     * Accept new order
     */
    public void acceptOrder() {
        if (!isAvailable()) {
            throw new IllegalStateException("Maximum concurrent orders reached");
        }
        currentOrders++;
    }

    /**
     * Complete order
     */
    public void completeOrder() {
        if (currentOrders <= 0) {
            throw new IllegalStateException("No orders to complete");
        }
        currentOrders--;
    }
}
