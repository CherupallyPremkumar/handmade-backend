package com.homebase.ecom.entitystore.impl;

import com.homebase.ecom.domain.TaxRate;
import com.homebase.ecom.entitystore.TaxEntityStore;
import com.homebase.ecom.repository.TaxRepository;
import org.chenile.utils.entity.service.EntityStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TaxEntityStoreImpl implements EntityStore<TaxRate>, TaxEntityStore {

    @Autowired
    private TaxRepository taxRepository;

    @Override
    public void store(TaxRate entity) {
        if (entity == null) return;
        // Convert domain to JPA entity if needed, otherwise save directly
        taxRepository.save(toEntity(entity));
    }

    @Override
    public TaxRate retrieve(String id) {
        return taxRepository.findById(id)
                .map(this::toDomain)
                .orElse(null);
    }

    @Override
    public TaxRate findByCountryAndStateAndCategory(String country, String state, String productCategory) {
        return taxRepository.findByCountryAndStateAndProductCategory(country, state, productCategory)
                .map(this::toDomain)
                .orElse(null);
    }

    // -----------------------
    // Conversion helpers
    // -----------------------

    private com.homebase.ecom.entity.TaxRateEntity toEntity(TaxRate domain) {
        if (domain == null) return null;
        com.homebase.ecom.entity.TaxRateEntity entity = new com.homebase.ecom.entity.TaxRateEntity();
        entity.setId(domain.getId());
        entity.setCountry(domain.getCountry());
        entity.setState(domain.getState());
        entity.setProductCategory(domain.getProductCategory());
        entity.setRate(domain.getRate());
        entity.setEffectiveFrom(domain.getEffectiveFrom());
        entity.setEffectiveTo(domain.getEffectiveTo());
        return entity;
    }

    private TaxRate toDomain(com.homebase.ecom.entity.TaxRateEntity entity) {
        if (entity == null) return null;
        TaxRate domain = new TaxRate();
        domain.setId(entity.getId());
        domain.setCountry(entity.getCountry());
        domain.setState(entity.getState());
        domain.setProductCategory(entity.getProductCategory());
        domain.setRate(entity.getRate());
        domain.setEffectiveFrom(entity.getEffectiveFrom());
        domain.setEffectiveTo(entity.getEffectiveTo());
        return domain;
    }
}