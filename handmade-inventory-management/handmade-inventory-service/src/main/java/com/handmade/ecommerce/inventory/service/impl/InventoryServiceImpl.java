package com.handmade.ecommerce.inventory.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.inventory.model.Inventory;
import com.handmade.ecommerce.inventory.service.InventoryService;

import com.handmade.ecommerce.inventory.configuration.dao.InventoryRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class InventoryServiceImpl implements InventoryService{
    private static final Logger logger = LoggerFactory.getLogger(InventoryServiceImpl.class);
    @Autowired
    InventoryRepository inventoryRepository;
    @Override
    public Inventory save(Inventory entity) {
        entity = inventoryRepository.save(entity);
        return entity;
    }

    @Override
    public Inventory retrieve(String id) {
        Optional<Inventory> entity = inventoryRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find inventory with ID " + id);
    }
}