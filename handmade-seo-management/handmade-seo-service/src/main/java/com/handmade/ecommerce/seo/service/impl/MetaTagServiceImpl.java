package com.handmade.ecommerce.seo.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.seo.model.MetaTag;
import com.handmade.ecommerce.seo.service.MetaTagService;

import com.handmade.ecommerce.seo.configuration.dao.MetaTagRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class MetaTagServiceImpl implements MetaTagService{
    private static final Logger logger = LoggerFactory.getLogger(MetaTagServiceImpl.class);
    @Autowired
    MetaTagRepository metaTagRepository;
    @Override
    public MetaTag save(MetaTag entity) {
        entity = metaTagRepository.save(entity);
        return entity;
    }

    @Override
    public MetaTag retrieve(String id) {
        Optional<MetaTag> entity = metaTagRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find seo with ID " + id);
    }
}