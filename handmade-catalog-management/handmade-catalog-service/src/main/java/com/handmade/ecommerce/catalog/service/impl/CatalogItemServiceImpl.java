package com.handmade.ecommerce.catalog.service.impl;

import com.handmade.ecommerce.catalog.model.CatalogItem;
import com.handmade.ecommerce.catalog.service.CatalogItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.handmade.ecommerce.catalog.configuration.dao.CatalogItemRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class CatalogItemServiceImpl implements CatalogItemService {
    private static final Logger logger = LoggerFactory.getLogger(CatalogItemServiceImpl.class);
    @Autowired
    CatalogItemRepository catalogRepository;
    @Override
    public CatalogItem save(CatalogItem entity) {
        entity = catalogRepository.save(entity);
        return entity;
    }

    @Override
    public CatalogItem retrieve(String id) {
        Optional<CatalogItem> entity = catalogRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find catalog with ID " + id);
    }
}