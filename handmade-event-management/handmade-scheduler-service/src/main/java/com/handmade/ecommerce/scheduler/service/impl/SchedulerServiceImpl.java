package com.handmade.ecommerce.scheduler.service.impl;

import com.handmade.ecommerce.event.model.Scheduler;
import com.handmade.ecommerce.event.service.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.handmade.ecommerce.scheduler.configuration.dao.SchedulerRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class SchedulerServiceImpl implements SchedulerService {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerServiceImpl.class);
    @Autowired
    SchedulerRepository schedulerRepository;
    @Override
    public Scheduler save(Scheduler entity) {
        entity = schedulerRepository.save(entity);
        return entity;
    }

    @Override
    public Scheduler retrieve(String id) {
        Optional<Scheduler> entity = schedulerRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find scheduler with ID " + id);
    }
}