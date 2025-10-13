package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Inventory;
import com.homebase.ecom.repository.InventoryRepository;
import org.chenile.utils.entity.service.EntityStore;

public class InventoryEntityStore implements EntityStore<Inventory> {


    private final InventoryRepository inventoryRepository;

    public InventoryEntityStore(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void store(Inventory entity) {

    }

    @Override
    public Inventory retrieve(String id) {
        return null;
    }
}
