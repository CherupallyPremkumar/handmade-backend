package com.handmade.ecommerce.messaging.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.messaging.model.Messaging;
import com.handmade.ecommerce.messaging.service.MessagingService;

import com.handmade.ecommerce.messaging.configuration.dao.MessagingRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class MessagingServiceImpl implements MessagingService{
    private static final Logger logger = LoggerFactory.getLogger(MessagingServiceImpl.class);
    @Autowired
    MessagingRepository messagingRepository;
    @Override
    public Messaging save(Messaging entity) {
        entity = messagingRepository.save(entity);
        return entity;
    }

    @Override
    public Messaging retrieve(String id) {
        Optional<Messaging> entity = messagingRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find messaging with ID " + id);
    }
}