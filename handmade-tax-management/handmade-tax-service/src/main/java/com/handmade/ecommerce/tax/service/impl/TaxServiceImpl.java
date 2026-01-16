package com.handmade.ecommerce.tax.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.tax.model.Tax;
import com.handmade.ecommerce.tax.service.TaxService;

import com.handmade.ecommerce.tax.configuration.dao.TaxRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class TaxServiceImpl implements TaxService{
    private static final Logger logger = LoggerFactory.getLogger(TaxServiceImpl.class);
    @Autowired
    TaxRepository taxRepository;
    @Override
    public Tax save(Tax entity) {
        entity = taxRepository.save(entity);
        return entity;
    }

    @Override
    public Tax retrieve(String id) {
        Optional<Tax> entity = taxRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find tax with ID " + id);
    }
}