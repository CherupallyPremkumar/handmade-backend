package com.handmade.ecommerce.governance.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.governance.model.Governance;
import com.handmade.ecommerce.governance.service.GovernanceService;

import com.handmade.ecommerce.governance.configuration.dao.GovernanceRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class GovernanceServiceImpl implements GovernanceService{
    private static final Logger logger = LoggerFactory.getLogger(GovernanceServiceImpl.class);
    @Autowired
    GovernanceRepository governanceRepository;
    @Override
    public Governance save(Governance entity) {
        entity = governanceRepository.save(entity);
        return entity;
    }

    @Override
    public Governance retrieve(String id) {
        Optional<Governance> entity = governanceRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find governance with ID " + id);
    }
}