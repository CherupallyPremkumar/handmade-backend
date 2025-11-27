package com.handmade.ecommerce.inventory;

import com.handmade.ecommerce.command.CreateInventoryCommand;

public interface InventoryService {
    void createInventory(CreateInventoryCommand inventory);
}
