package com.handmade.ecommerce.cash.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.cash.model.Cash;
import com.handmade.ecommerce.cash.service.CashService;

import com.handmade.ecommerce.cash.configuration.dao.CashRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class CashServiceImpl implements CashService{
    private static final Logger logger = LoggerFactory.getLogger(CashServiceImpl.class);
    @Autowired
    CashRepository cashRepository;
    @Override
    public Cash save(Cash entity) {
        entity = cashRepository.save(entity);
        return entity;
    }

    @Override
    public Cash retrieve(String id) {
        Optional<Cash> entity = cashRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find cash with ID " + id);
    }
}