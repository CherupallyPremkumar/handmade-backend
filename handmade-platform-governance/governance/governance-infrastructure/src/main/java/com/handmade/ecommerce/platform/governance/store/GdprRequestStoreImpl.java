package com.handmade.ecommerce.platform.governance.store;

import com.handmade.ecommerce.platform.governance.entity.GdprRequest;
import com.handmade.ecommerce.platform.governance.repository.GdprRequestRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * EntityStore implementation for GdprRequest.
 */
public class GdprRequestStoreImpl implements EntityStore<GdprRequest> {

    @Autowired
    private GdprRequestRepository gdprRequestRepository;

    @Override
    public GdprRequest retrieve(String id) {
        return gdprRequestRepository.findById(id).orElse(null);
    }

    @Override
    public void store(GdprRequest entity) {
        gdprRequestRepository.save(entity);
    }

}
