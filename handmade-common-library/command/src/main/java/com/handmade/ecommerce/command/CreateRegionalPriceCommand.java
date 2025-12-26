package com.handmade.ecommerce.command;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * CreateRegionalPriceCommand
 * DTO for creating region-specific pricing
 * 
 * Scalability: Supports unlimited regions without code changes
 */
public class CreateRegionalPriceCommand {

    private String region; // ISO country code: "US", "UK", "EU", "CA"
    private String currency; // ISO currency: "USD", "GBP", "EUR", "CAD"
    private BigDecimal priceAmount; // Price in local currency
    private BigDecimal taxRate; // Tax percentage (e.g., 20 for 20%)
    private Boolean includesTax; // true = price includes tax, false = tax added at checkout
    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;

    // Getters and Setters
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getPriceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(BigDecimal priceAmount) {
        this.priceAmount = priceAmount;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public Boolean getIncludesTax() {
        return includesTax;
    }

    public void setIncludesTax(Boolean includesTax) {
        this.includesTax = includesTax;
    }

    public LocalDateTime getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(LocalDateTime effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public LocalDateTime getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(LocalDateTime effectiveTo) {
        this.effectiveTo = effectiveTo;
    }
}
