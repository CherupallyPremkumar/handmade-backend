package com.handmade.ecommerce.inventory.delegate;

import com.handmade.ecommerce.inventory.api.InventoryService;
import com.handmade.ecommerce.inventory.command.CreateInventoryCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InventoryManagerClientImpl implements InventoryManagerClient {

    @Autowired
    private InventoryService inventoryService;

    @Override
    public void createInventory(CreateInventoryCommand command) {
        inventoryService.createInventory(command);
    }

    @Override
    public int getAvailableQuantity(String variantId) {
        return inventoryService.getAvailableQuantity(variantId);
    }

    @Override
    public void updateStock(String variantId, int delta) {
        inventoryService.updateStock(variantId, delta);
    }
}
