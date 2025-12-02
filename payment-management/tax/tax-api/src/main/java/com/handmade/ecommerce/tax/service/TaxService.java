package com.handmade.ecommerce.tax.service;

import com.handmade.ecommerce.tax.model.Tax;

public interface TaxService {
    boolean validateTaxId(String countryCode,String taxId);
}
