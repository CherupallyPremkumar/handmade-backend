package com.handmade.ecommerce.search.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.search.model.Search;
import com.handmade.ecommerce.search.service.SearchService;

import com.handmade.ecommerce.search.configuration.dao.SearchRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class SearchServiceImpl implements SearchService{
    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);
    @Autowired
    SearchRepository searchRepository;
    @Override
    public Search save(Search entity) {
        entity = searchRepository.save(entity);
        return entity;
    }

    @Override
    public Search retrieve(String id) {
        Optional<Search> entity = searchRepository.findById(id);
        if (entity.isPresent()) return entity.get();
        throw new NotFoundException(1500,"Unable to find search with ID " + id);
    }
}