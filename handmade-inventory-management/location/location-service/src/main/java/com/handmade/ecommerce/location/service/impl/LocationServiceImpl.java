package com.handmade.ecommerce.location.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.location.model.Location;
import com.handmade.ecommerce.location.service.LocationService;

import com.handmade.ecommerce.location.configuration.dao.LocationRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class LocationServiceImpl implements LocationService{
    private static final Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);
    @Autowired
    LocationRepository locationRepository;
    @Override
    public Location save(Location entity) {
        entity = locationRepository.save(entity);
        return entity;
    }

    @Override
    public Location retrieve(String id) {
        Optional<Location> entity = locationRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find location with ID " + id);
    }
}