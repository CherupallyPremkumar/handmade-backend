package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.Price;
import com.homebase.ecom.repository.PriceRepository;
import org.chenile.utils.entity.service.EntityStore;

public class PriceEntityStore implements EntityStore<Price> {


    private final PriceRepository priceRepository;

    public PriceEntityStore(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public void store(Price entity) {
        // Implementation to store Price entity
    }

    @Override
    public Price retrieve(String id) {
        // Implementation to retrieve Price entity by ID
        return null;
    }
}
