package com.homebase.ecom.entitystore;

import com.homebase.ecom.domain.TaxRate;

import java.util.Optional;

public interface TaxEntityStore {
    TaxRate findByCountryAndStateAndCategory(String country, String state, String productCategory);
}
