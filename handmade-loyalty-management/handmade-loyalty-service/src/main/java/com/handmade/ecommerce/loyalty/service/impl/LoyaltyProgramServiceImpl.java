package com.handmade.ecommerce.loyalty.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.loyalty.model.LoyaltyProgram;
import com.handmade.ecommerce.loyalty.service.LoyaltyProgramService;

import com.handmade.ecommerce.loyalty.configuration.dao.LoyaltyProgramRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class LoyaltyProgramServiceImpl implements LoyaltyProgramService{
    private static final Logger logger = LoggerFactory.getLogger(LoyaltyProgramServiceImpl.class);
    @Autowired
    LoyaltyProgramRepository loyaltyProgramRepository;
    @Override
    public LoyaltyProgram save(LoyaltyProgram entity) {
        entity = loyaltyProgramRepository.save(entity);
        return entity;
    }

    @Override
    public LoyaltyProgram retrieve(String id) {
        Optional<LoyaltyProgram> entity = loyaltyProgramRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find loyalty with ID " + id);
    }
}