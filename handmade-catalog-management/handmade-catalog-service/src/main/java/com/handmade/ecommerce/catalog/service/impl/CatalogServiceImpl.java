package com.handmade.ecommerce.catalog.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.catalog.model.Catalog;
import com.handmade.ecommerce.catalog.service.CatalogService;

import com.handmade.ecommerce.catalog.configuration.dao.CatalogRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class CatalogServiceImpl implements CatalogService{
    private static final Logger logger = LoggerFactory.getLogger(CatalogServiceImpl.class);
    @Autowired
    CatalogRepository catalogRepository;
    @Override
    public Catalog save(Catalog entity) {
        entity = catalogRepository.save(entity);
        return entity;
    }

    @Override
    public Catalog retrieve(String id) {
        Optional<Catalog> entity = catalogRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find catalog with ID " + id);
    }
}