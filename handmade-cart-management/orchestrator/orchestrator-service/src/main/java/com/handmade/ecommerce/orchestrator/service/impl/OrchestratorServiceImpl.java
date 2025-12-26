package com.handmade.ecommerce.orchestrator.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.orchestrator.model.Orchestrator;
import com.handmade.ecommerce.orchestrator.service.OrchestratorService;

import com.handmade.ecommerce.orchestrator.configuration.dao.OrchestratorRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class OrchestratorServiceImpl implements OrchestratorService{
    private static final Logger logger = LoggerFactory.getLogger(OrchestratorServiceImpl.class);
    @Autowired
    OrchestratorRepository orchestratorRepository;
    @Override
    public Orchestrator save(Orchestrator entity) {
        entity = orchestratorRepository.save(entity);
        return entity;
    }

    @Override
    public Orchestrator retrieve(String id) {
        Optional<Orchestrator> entity = orchestratorRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find orchestrator with ID " + id);
    }
}