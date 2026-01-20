package com.handmade.ecommerce.comparison.service.impl;

import com.handmade.ecommerce.comparison.model.ComparisonItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.comparison.service.ComparisonItemService;

import com.handmade.ecommerce.comparison.configuration.dao.ComparisonItemRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class ComparisonItemServiceImpl implements ComparisonItemService{
    private static final Logger logger = LoggerFactory.getLogger(ComparisonItemServiceImpl.class);
    @Autowired
    ComparisonItemRepository comparisonRepository;
    @Override
    public ComparisonItem save(ComparisonItem entity) {
        entity = comparisonRepository.save(entity);
        return entity;
    }

    @Override
    public ComparisonItem retrieve(String id) {
        Optional<ComparisonItem> entity = comparisonRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find comparison with ID " + id);
    }
}