package com.handmade.ecommerce.integration.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.integration.model.Integration;
import com.handmade.ecommerce.integration.service.IntegrationService;

import com.handmade.ecommerce.integration.configuration.dao.IntegrationRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class IntegrationServiceImpl implements IntegrationService{
    private static final Logger logger = LoggerFactory.getLogger(IntegrationServiceImpl.class);
    @Autowired
    IntegrationRepository integrationRepository;
    @Override
    public Integration save(Integration entity) {
        entity = integrationRepository.save(entity);
        return entity;
    }

    @Override
    public Integration retrieve(String id) {
        Optional<Integration> entity = integrationRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find integration with ID " + id);
    }
}