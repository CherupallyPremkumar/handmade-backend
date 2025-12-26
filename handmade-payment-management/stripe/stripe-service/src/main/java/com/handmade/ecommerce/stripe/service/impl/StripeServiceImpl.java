package com.handmade.ecommerce.stripe.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.stripe.model.Stripe;
import com.handmade.ecommerce.stripe.service.StripeService;

import com.handmade.ecommerce.stripe.configuration.dao.StripeRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class StripeServiceImpl implements StripeService{
    private static final Logger logger = LoggerFactory.getLogger(StripeServiceImpl.class);
    @Autowired
    StripeRepository stripeRepository;
    @Override
    public Stripe save(Stripe entity) {
        entity = stripeRepository.save(entity);
        return entity;
    }

    @Override
    public Stripe retrieve(String id) {
        Optional<Stripe> entity = stripeRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find stripe with ID " + id);
    }
}