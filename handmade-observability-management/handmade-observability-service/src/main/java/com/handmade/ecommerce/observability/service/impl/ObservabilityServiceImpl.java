package com.handmade.ecommerce.observability.service.impl;

import com.handmade.ecommerce.observability.configuration.dao.MetricsSnapshotRepository;
import com.handmade.ecommerce.observability.model.MetricsSnapshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.observability.service.ObservabilityService;

import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class ObservabilityServiceImpl implements ObservabilityService{
    private static final Logger logger = LoggerFactory.getLogger(ObservabilityServiceImpl.class);
    @Autowired
    MetricsSnapshotRepository metricsSnapshotRepository;
    @Override
    public MetricsSnapshot save(MetricsSnapshot entity) {
        entity = metricsSnapshotRepository.save(entity);
        return entity;
    }

    @Override
    public MetricsSnapshot retrieve(String id) {
        Optional<MetricsSnapshot> entity = metricsSnapshotRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find observability with ID " + id);
    }
}