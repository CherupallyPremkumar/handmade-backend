package com.handmade.ecommerce.shipping.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.shipping.model.Shipping;
import com.handmade.ecommerce.shipping.service.ShippingService;

import com.handmade.ecommerce.shipping.configuration.dao.ShippingRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class ShippingServiceImpl implements ShippingService{
    private static final Logger logger = LoggerFactory.getLogger(ShippingServiceImpl.class);
    @Autowired
    ShippingRepository shippingRepository;
    @Override
    public Shipping save(Shipping entity) {
        entity = shippingRepository.save(entity);
        return entity;
    }

    @Override
    public Shipping retrieve(String id) {
        Optional<Shipping> entity = shippingRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find shipping with ID " + id);
    }
}