package com.handmade.ecommerce.inventory.delegate;

import com.handmade.ecommerce.inventory.api.InventoryService;
import com.handmade.ecommerce.inventory.command.CreateInventoryCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Client for interacting with the Inventory Service.
 */
public interface InventoryManagerClient {
    void createInventory(CreateInventoryCommand command);

    int getAvailableQuantity(String variantId);

    void updateStock(String variantId, int delta);
}
