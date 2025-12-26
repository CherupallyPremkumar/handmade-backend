package com.handmade.ecommerce.inventory.service;

import com.handmade.ecommerce.command.CreateInventoryCommand;

public interface InventoryService {

    /**
     * Create new inventory
     */
    void createInventory(CreateInventoryCommand inventory);

    /**
     * Get available stock for a variant
     * 
     * @param variantId Variant ID
     * @return Available stock quantity, or null if not found
     */
    Integer getAvailableStock(String variantId);
}
