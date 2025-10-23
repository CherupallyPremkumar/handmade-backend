package com.homebase.ecom.entitystore.impl;

import com.homebase.ecom.domain.PriceLine;
import com.homebase.ecom.entity.PriceLineEntity;
import com.homebase.ecom.entitystore.PriceEntityStore;
import com.homebase.ecom.entitystore.PriceLineEntityStore;
import com.homebase.ecom.repository.PriceLineRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Entity store for PriceLine with caching support
 */
public class PriceLineEntityStoreImpl implements EntityStore<PriceLine> , PriceLineEntityStore {

    private final PriceLineRepository priceLineRepository;

    public PriceLineEntityStoreImpl(PriceLineRepository priceLineRepository) {
        this.priceLineRepository = priceLineRepository;
    }

    @Override
    @CacheEvict(value = "priceLines", key = "#priceLine.priceId + ':' + #priceLine.currency")
    public void store(PriceLine priceLine) {
        PriceLineEntity entity = toEntity(priceLine);
        priceLineRepository.save(entity);
        priceLine.setId(entity.getId()); // set generated ID back to domain
    }

    @Override
    @Cacheable(value = "priceLines", key = "#id", unless = "#result == null")
    public PriceLine retrieve(String id) {
        Optional<PriceLineEntity> entityOpt = priceLineRepository.findById(id);
        return entityOpt.map(this::toDomain).orElse(null);
    }

    @Override
    @Cacheable(value = "priceLines", key = "#priceId + ':' + #currency", unless = "#result == null")
    public PriceLine findByPriceIdAndCurrency(String priceId, String currency) {
        PriceLineEntity entity = priceLineRepository.findByPriceIdAndCurrency(priceId, currency)
                .orElseThrow(() -> new IllegalStateException(
                        "PriceLine not found for priceId=" + priceId + " and currency=" + currency
                ));
        return toDomain(entity);
    }

    // --- Conversion helpers ---
    private PriceLineEntity toEntity(PriceLine priceLine) {
        PriceLineEntity entity = new PriceLineEntity();
        entity.setId(priceLine.getId());
        entity.setPriceId(priceLine.getPriceId());
        entity.setRegion(priceLine.getRegion());
        entity.setCurrency(priceLine.getCurrency());
        entity.setPriceType(priceLine.getPriceType());
        entity.setBaseAmount(priceLine.getAmount());
        entity.setSaleAmount(priceLine.getSaleAmount());
        entity.setDiscountPercentage(priceLine.getDiscountPercentage());
        entity.setSaleStartDate(priceLine.getSaleStartDate());
        entity.setSaleEndDate(priceLine.getSaleEndDate());
        // add other fields if needed
        return entity;
    }

    private PriceLine toDomain(PriceLineEntity entity) {
        PriceLine priceLine = new PriceLine();
        priceLine.setId(entity.getId());
        priceLine.setPriceId(entity.getPriceId());
        priceLine.setRegion(entity.getRegion());
        priceLine.setCurrency(entity.getCurrency());
        priceLine.setPriceType(entity.getPriceType());
        priceLine.setAmount(entity.getBaseAmount());
        priceLine.setSaleAmount(entity.getSaleAmount());
        priceLine.setDiscountPercentage(entity.getDiscountPercentage());
        priceLine.setSaleStartDate(entity.getSaleStartDate());
        priceLine.setSaleEndDate(entity.getSaleEndDate());
        // add other fields if needed
        return priceLine;
    }
}
