package com.handmade.ecommerce.customer.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.customer.model.Customer;
import com.handmade.ecommerce.customer.service.CustomerService;

import com.handmade.ecommerce.customer.configuration.dao.CustomerRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class CustomerServiceImpl implements CustomerService{
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public Customer save(Customer entity) {
        entity = customerRepository.save(entity);
        return entity;
    }

    @Override
    public Customer retrieve(String id) {
        Optional<Customer> entity = customerRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find customer with ID " + id);
    }
}