package com.handmade.ecommerce.tax;

import com.handmade.ecommerce.tax.model.Tax;
import com.handmade.ecommerce.tax.service.TaxCentricService;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TaxServiceRegistry {

    private final Map<String, TaxCentricService> serviceMap;

    public TaxServiceRegistry(Map<String, TaxCentricService> serviceMap) {
        this.serviceMap = serviceMap;
    }


    public TaxCentricService getService(String countryCode) {
        TaxCentricService service = serviceMap.get(countryCode.toUpperCase());
        if (service == null) {
            throw new IllegalArgumentException("No TaxCentricService registered for country: " + countryCode);
        }
        return service;
    }

}
