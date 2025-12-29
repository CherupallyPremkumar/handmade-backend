package com.handmade.ecommerce.reconciliation.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.reconciliation.model.Reconciliation;
import com.handmade.ecommerce.reconciliation.service.ReconciliationService;

import com.handmade.ecommerce.reconciliation.configuration.dao.ReconciliationRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class ReconciliationServiceImpl implements ReconciliationService{
    private static final Logger logger = LoggerFactory.getLogger(ReconciliationServiceImpl.class);
    @Autowired
    ReconciliationRepository reconciliationRepository;
    @Override
    public Reconciliation save(Reconciliation entity) {
        entity = reconciliationRepository.save(entity);
        return entity;
    }

    @Override
    public Reconciliation retrieve(String id) {
        Optional<Reconciliation> entity = reconciliationRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find reconciliation with ID " + id);
    }
}