package com.handmade.ecommerce.customer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.customer.model.CustomerProfile;

import com.handmade.ecommerce.customer.configuration.dao.CustomerProfileRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class CustomerProfileServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(CustomerProfileServiceImpl.class);
    @Autowired
    CustomerProfileRepository customerProfileRepository;

    public CustomerProfile save(CustomerProfile entity) {
        entity = customerProfileRepository.save(entity);
        return entity;
    }


    public CustomerProfile retrieve(String id) {
        Optional<CustomerProfile> entity = customerProfileRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find customer with ID " + id);
    }
}