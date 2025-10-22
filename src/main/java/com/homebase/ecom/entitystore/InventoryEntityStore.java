package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Inventory;
import org.chenile.utils.entity.service.EntityStore;

public interface InventoryEntityStore<T>  extends EntityStore<T> {

    public Inventory findByProductVariantId(String variantId);
}
