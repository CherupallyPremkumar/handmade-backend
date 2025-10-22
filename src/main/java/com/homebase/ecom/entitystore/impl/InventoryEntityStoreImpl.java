package com.homebase.ecom.entitystore.impl;

import com.homebase.ecom.constants.HmHeaderUtils;
import com.homebase.ecom.domain.Inventory;
import com.homebase.ecom.entity.InventoryEntity;
import com.homebase.ecom.entitystore.InventoryEntityStore;
import com.homebase.ecom.repository.InventoryRepository;
import org.chenile.core.context.ContextContainer;

import static org.chenile.core.context.ContextContainer.CONTEXT_CONTAINER;


public class InventoryEntityStoreImpl implements InventoryEntityStore<Inventory> {


    private final InventoryRepository inventoryRepository;

    public InventoryEntityStoreImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public void store(Inventory entity) {
        InventoryEntity inventoryEntity = toEntity(entity);
        inventoryRepository.save(inventoryEntity);
    }

    @Override
    public Inventory retrieve(String id) {
        InventoryEntity entity = inventoryRepository.findById(id).orElse(null);
        return fromEntity(entity);
    }

    @Override
    public Inventory findByProductVariantId(String variantId) {
        String subTenantId = HmHeaderUtils.getSubTenantIdKey(CONTEXT_CONTAINER.getContext());
        InventoryEntity entity = inventoryRepository.findByProductVariantIdAndSubTenantId(variantId, subTenantId);
        return fromEntity(entity);
    }

    public static Inventory fromEntity(InventoryEntity entity) {
        if (entity == null) return null;

        Inventory inventory = new Inventory();
        inventory.setId(entity.getId());
        inventory.setCurrentState(entity.getCurrentState());
        inventory.setSubTenantId(entity.getSubTenantId());
        inventory.setProductVariantId(entity.getProductVariantId());
        inventory.setLocationId(entity.getLocationId());
        inventory.setQuantityOnHand(entity.getQuantityOnHand());
        inventory.setQuantityCommitted(entity.getQuantityCommitted());
        inventory.setQuantityAvailable(entity.getQuantityAvailable());
        inventory.setQuantityBackOrdered(entity.getQuantityBackOrdered());

        return inventory;
    }

    // Convert domain object to JPA entity
    public static InventoryEntity toEntity(Inventory inventory) {
        if (inventory == null) return null;

        InventoryEntity entity = new InventoryEntity();
        entity.setId(inventory.getId());
        entity.setCurrentState(inventory.getCurrentState());
        entity.setSubTenantId(inventory.getSubTenantId());
        entity.setProductVariantId(inventory.getProductVariantId());
        entity.setLocationId(inventory.getLocationId());
        entity.setQuantityOnHand(inventory.getQuantityOnHand());
        entity.setQuantityCommitted(inventory.getQuantityCommitted());
        entity.setQuantityAvailable(inventory.getQuantityAvailable());
        entity.setQuantityBackOrdered(inventory.getQuantityBackOrdered());

        return entity;
    }
}
