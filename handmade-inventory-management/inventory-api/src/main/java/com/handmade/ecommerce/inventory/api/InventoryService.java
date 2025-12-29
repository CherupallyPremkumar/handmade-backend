package com.handmade.ecommerce.inventory.api;

import com.handmade.ecommerce.inventory.command.CreateInventoryCommand;

/**
 * Inventory Service Interface
 * Manages product availability and stock levels
 */
public interface InventoryService {
    /**
     * Initialize inventory for a new variant
     */
    void createInventory(CreateInventoryCommand command);

    /**
     * Get available quantity for a variant
     */
    int getAvailableQuantity(String variantId);

    /**
     * Update stock level
     */
    void updateStock(String variantId, int delta);
}
