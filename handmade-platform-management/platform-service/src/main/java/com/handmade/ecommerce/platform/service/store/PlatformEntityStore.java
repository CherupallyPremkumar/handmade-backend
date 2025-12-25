package com.handmade.ecommerce.platform.service.store;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import org.chenile.utils.entity.service.EntityStore;

public class PlatformEntityStore implements EntityStore<PlatformOwner> {
    @Override
    public void store(PlatformOwner entity) {

    }

    @Override
    public PlatformOwner retrieve(String id) {
        return null;
    }
}
