package com.handmade.ecommerce.platform.service.store;

import com.handmade.ecommerce.platform.domain.aggregate.PlatformOwner;
import com.handmade.ecommerce.platform.configuration.dao.PlatformRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;

public class PlatformEntityStore implements EntityStore<PlatformOwner> {
    
    @Autowired
    private PlatformRepository platformRepository;
    
    @Override
    public void store(PlatformOwner entity) {
        platformRepository.save(entity);
    }

    @Override
    public PlatformOwner retrieve(String id) {
        return platformRepository.findById(id).orElse(null);
    }
}
