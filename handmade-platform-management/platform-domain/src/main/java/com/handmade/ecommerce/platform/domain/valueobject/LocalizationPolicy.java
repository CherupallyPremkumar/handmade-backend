package com.handmade.ecommerce.platform.domain.valueobject;

import java.io.Serializable;

/**
 * LocalizationPolicy: Defines supported regions, currencies, and languages.
 * Value Object - PURE DOMAIN MODEL.
 */
public class LocalizationPolicy implements Serializable {

    private String primaryCurrency; // e.g., "USD"
    private String supportedCurrencies; // CSV, e.g., "USD,EUR,GBP"
    private String defaultLanguage; // e.g., "en"
    private String supportedLanguages; // CSV, e.g., "en,fr,es"
    private String timezone; // e.g., "UTC"

    public String getPrimaryCurrency() {
        return primaryCurrency;
    }

    public void setPrimaryCurrency(String primaryCurrency) {
        this.primaryCurrency = primaryCurrency;
    }

    public String getSupportedCurrencies() {
        return supportedCurrencies;
    }

    public void setSupportedCurrencies(String supportedCurrencies) {
        this.supportedCurrencies = supportedCurrencies;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public String getSupportedLanguages() {
        return supportedLanguages;
    }

    public void setSupportedLanguages(String supportedLanguages) {
        this.supportedLanguages = supportedLanguages;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }
}
