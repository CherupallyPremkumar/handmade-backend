package com.handmade.ecommerce.events.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.events.model.Events;
import com.handmade.ecommerce.events.service.EventsService;

import com.handmade.ecommerce.events.configuration.dao.EventsRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class EventsServiceImpl implements EventsService{
    private static final Logger logger = LoggerFactory.getLogger(EventsServiceImpl.class);
    @Autowired
    EventsRepository eventsRepository;
    @Override
    public Events save(Events entity) {
        entity = eventsRepository.save(entity);
        return entity;
    }

    @Override
    public Events retrieve(String id) {
        Optional<Events> entity = eventsRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find events with ID " + id);
    }
}