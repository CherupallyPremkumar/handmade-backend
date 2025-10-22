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
        entity.setCurrency(domain.getCurrency());

        // Convert Double amount to String
        if (domain.getAmount() != null) {
            entity.setPrice(String.valueOf(domain.getAmount()));
        }

        // Convert Double discountPercentage to BigDecimal
        if (domain.getDiscountPercentage() != null) {
            entity.setDiscountPercentage(BigDecimal.valueOf(domain.getDiscountPercentage()));
        } else {
            entity.setDiscountPercentage(BigDecimal.ZERO);
        }

        return entity;
    }

    private Price toDomain(PriceEntity entity) {
        if (entity == null) return null;

        Price domain = new Price();
        domain.setId(entity.getId());
        domain.setProductVariantId(entity.getProductVariantId());
        domain.setCurrency(entity.getCurrency());

        // Convert String price to Double
        try {
            if (entity.getPrice() != null) {
                domain.setAmount(Double.parseDouble(entity.getPrice()));
            }
        } catch (NumberFormatException e) {
            domain.setAmount(0.0);
        }

        // Convert BigDecimal discountPercentage to Double
        if (entity.getDiscountPercentage() != null) {
            domain.setDiscountPercentage(entity.getDiscountPercentage().doubleValue());
        } else {
            domain.setDiscountPercentage(0.0);
        }

        return domain;
    }
}