package com.homebase.ecom.dto.response;

import java.math.BigDecimal;

/**
 * Tax information for a price
 */
public class PriceTaxInfo {

    private String taxCategory;
    private BigDecimal taxRate;
    private BigDecimal taxAmount;
    private BigDecimal priceWithTax;

    // Getters and Setters
    public String getTaxCategory() {
        return taxCategory;
    }

    public void setTaxCategory(String taxCategory) {
        this.taxCategory = taxCategory;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getPriceWithTax() {
        return priceWithTax;
    }

    public void setPriceWithTax(BigDecimal priceWithTax) {
        this.priceWithTax = priceWithTax;
    }
}
