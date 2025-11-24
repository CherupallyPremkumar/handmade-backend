package com.handmade.ecommerce.common.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.common.model.Common;
import com.handmade.ecommerce.common.service.CommonService;

import com.handmade.ecommerce.common.configuration.dao.CommonRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class CommonServiceImpl implements CommonService{
    private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);
    @Autowired
    CommonRepository commonRepository;
    @Override
    public Common save(Common entity) {
        entity = commonRepository.save(entity);
        return entity;
    }

    @Override
    public Common retrieve(String id) {
        Optional<Common> entity = commonRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find common with ID " + id);
    }
}