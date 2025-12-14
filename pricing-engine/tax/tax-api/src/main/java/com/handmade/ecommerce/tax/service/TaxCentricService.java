package com.handmade.ecommerce.tax.service;

public interface TaxCentricService {
    void validateTaxId(String taxId);
    public boolean verifyWithTaxPortal(String taxId);
}
