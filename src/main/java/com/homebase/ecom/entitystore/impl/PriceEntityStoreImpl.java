package com.homebase.ecom.entitystore.impl;

import com.homebase.ecom.domain.Price;
import com.homebase.ecom.entity.PriceEntity;
import com.homebase.ecom.entitystore.PriceEntityStore;
import com.homebase.ecom.repository.PriceRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class PriceEntityStoreImpl implements PriceEntityStore {

    private final PriceRepository priceRepository;

    public PriceEntityStoreImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public void store(Price domain) {
        priceRepository.save(toEntity(domain));
    }

    @Override
    public Price retrieve(String id) {
        return priceRepository.findById(id)
                .map(this::toDomain)
                .orElse(null);
    }

    @Override
    public Price findPriceByProductVariantId(String productVariantId) {
        PriceEntity entity = priceRepository.findByProductVariantId(productVariantId);
        return toDomain(entity);
    }

    private PriceEntity toEntity(Price domain) {
        if (domain == null) return null;

        PriceEntity entity = new PriceEntity();
        entity.setId(domain.getId());
        entity.setProductVariantId(domain.getProductVariantId());
        return entity;
    }

    private Price toDomain(PriceEntity entity) {
        if (entity == null) return null;
        Price domain = new Price();
        domain.setProductVariantId(entity.getProductVariantId());
        return domain;
    }
}