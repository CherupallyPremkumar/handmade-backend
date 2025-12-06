package com.handmade.ecommerce.tax.service;

import com.handmade.ecommerce.core.model.Money;
import com.handmade.ecommerce.tax.model.Tax;

public interface TaxService {

    /**
     * Validate tax ID for a country
     */
    boolean validateTaxId(String countryCode, String taxId);

    /**
     * Calculate tax amount for a given taxable amount
     * 
     * @param taxableAmount The amount to calculate tax on
     * @return Tax amount
     */
    Money calculateTax(Money taxableAmount);
}
