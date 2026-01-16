package com.handmade.ecommerce.loyalty.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.loyalty.model.Loyalty;
import com.handmade.ecommerce.loyalty.service.LoyaltyService;

import com.handmade.ecommerce.loyalty.configuration.dao.LoyaltyRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class LoyaltyServiceImpl implements LoyaltyService{
    private static final Logger logger = LoggerFactory.getLogger(LoyaltyServiceImpl.class);
    @Autowired
    LoyaltyRepository loyaltyRepository;
    @Override
    public Loyalty save(Loyalty entity) {
        entity = loyaltyRepository.save(entity);
        return entity;
    }

    @Override
    public Loyalty retrieve(String id) {
        Optional<Loyalty> entity = loyaltyRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find loyalty with ID " + id);
    }
}