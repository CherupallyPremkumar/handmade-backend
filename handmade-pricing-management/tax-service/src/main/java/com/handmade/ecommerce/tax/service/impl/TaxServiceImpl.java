package com.handmade.ecommerce.tax.service.impl;

import com.handmade.ecommerce.tax.TaxServiceRegistry;
import com.handmade.ecommerce.tax.service.TaxCentricService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.handmade.ecommerce.tax.model.Tax;
import com.handmade.ecommerce.tax.service.TaxService;

import com.handmade.ecommerce.tax.configuration.dao.TaxRepository;
import org.chenile.base.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Optional;

public class TaxServiceImpl implements TaxService{

    @Autowired
    TaxServiceRegistry taxServiceRegistry;

    private static final Logger logger = LoggerFactory.getLogger(TaxServiceImpl.class);

    @Override
    public boolean validateTaxId(String countryCode,String taxId) {
        TaxCentricService centricService=taxServiceRegistry.getService(countryCode);
        centricService.validateTaxId(taxId);
        return false;
    }
}