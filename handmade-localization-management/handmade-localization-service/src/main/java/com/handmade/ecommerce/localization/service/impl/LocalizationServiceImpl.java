package com.handmade.ecommerce.localization.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.localization.model.Localization;
import com.handmade.ecommerce.localization.service.LocalizationService;

import com.handmade.ecommerce.localization.configuration.dao.LocalizationRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class LocalizationServiceImpl implements LocalizationService{
    private static final Logger logger = LoggerFactory.getLogger(LocalizationServiceImpl.class);
    @Autowired
    LocalizationRepository localizationRepository;
    @Override
    public Localization save(Localization entity) {
        entity = localizationRepository.save(entity);
        return entity;
    }

    @Override
    public Localization retrieve(String id) {
        Optional<Localization> entity = localizationRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find localization with ID " + id);
    }
}