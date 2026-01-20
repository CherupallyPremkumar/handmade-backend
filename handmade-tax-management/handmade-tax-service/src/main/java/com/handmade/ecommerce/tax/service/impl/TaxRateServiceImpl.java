package com.handmade.ecommerce.tax.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.tax.model.TaxRate;

import com.handmade.ecommerce.tax.configuration.dao.TaxRateRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class TaxRateServiceImpl{
    private static final Logger logger = LoggerFactory.getLogger(TaxRateServiceImpl.class);
    @Autowired
    TaxRateRepository taxRateRepository;

    public TaxRate save(TaxRate entity) {
        entity = taxRateRepository.save(entity);
        return entity;
    }


    public TaxRate retrieve(String id) {
        Optional<TaxRate> entity = taxRateRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find tax with ID " + id);
    }
}