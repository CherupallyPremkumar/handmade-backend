package com.handmade.ecommerce.seo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.seo.model.Seo;
import com.handmade.ecommerce.seo.service.SeoService;

import com.handmade.ecommerce.seo.configuration.dao.SeoRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class SeoServiceImpl implements SeoService{
    private static final Logger logger = LoggerFactory.getLogger(SeoServiceImpl.class);
    @Autowired
    SeoRepository seoRepository;
    @Override
    public Seo save(Seo entity) {
        entity = seoRepository.save(entity);
        return entity;
    }

    @Override
    public Seo retrieve(String id) {
        Optional<Seo> entity = seoRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find seo with ID " + id);
    }
}