package com.handmade.ecommerce.razorpay.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.razorpay.model.Razorpay;
import com.handmade.ecommerce.razorpay.service.RazorpayService;

import com.handmade.ecommerce.razorpay.configuration.dao.RazorpayRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class RazorpayServiceImpl implements RazorpayService{
    private static final Logger logger = LoggerFactory.getLogger(RazorpayServiceImpl.class);
    @Autowired
    RazorpayRepository razorpayRepository;
    @Override
    public Razorpay save(Razorpay entity) {
        entity = razorpayRepository.save(entity);
        return entity;
    }

    @Override
    public Razorpay retrieve(String id) {
        Optional<Razorpay> entity = razorpayRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find razorpay with ID " + id);
    }
}