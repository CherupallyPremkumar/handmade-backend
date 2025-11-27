package com.handmade.ecommerce.inventory.service;

import com.handmade.ecommerce.command.CreateInventoryCommand;
import org.chenile.core.context.HeaderUtils;

public interface InventoryService {

    void createInventory(CreateInventoryCommand inventory);
}
