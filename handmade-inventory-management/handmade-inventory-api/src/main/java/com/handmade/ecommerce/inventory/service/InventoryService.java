package com.handmade.ecommerce.inventory.service;

import com.handmade.ecommerce.inventory.model.Inventory;

public interface InventoryService {
	// Define your interface here
    public Inventory save(Inventory inventory);
    public Inventory retrieve(String id);
}
