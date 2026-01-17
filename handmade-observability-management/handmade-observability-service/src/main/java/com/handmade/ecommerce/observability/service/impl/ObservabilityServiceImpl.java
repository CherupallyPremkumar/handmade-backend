package com.handmade.ecommerce.observability.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.observability.service.ObservabilityService;

import com.handmade.ecommerce.observability.configuration.dao.ObservabilityRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class ObservabilityServiceImpl implements ObservabilityService{
    private static final Logger logger = LoggerFactory.getLogger(ObservabilityServiceImpl.class);
    @Autowired
    ObservabilityRepository observabilityRepository;
    @Override
    public Observability save(Observability entity) {
        entity = observabilityRepository.save(entity);
        return entity;
    }

    @Override
    public Observability retrieve(String id) {
        Optional<Observability> entity = observabilityRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find observability with ID " + id);
    }
}