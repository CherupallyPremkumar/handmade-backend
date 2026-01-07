package com.handmade.ecommerce.platform.marketplace.service;

import com.handmade.ecommerce.platform.domain.entity.MarketplaceLocale;
import com.handmade.ecommerce.platform.marketplace.repository.MarketplaceLocaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service to manage MarketplaceLocale
 * Handles locale creation and configuration for marketplaces
 */
@Service
public class MarketplaceLocaleService {
    
    @Autowired
    private MarketplaceLocaleRepository localeRepository;
    
    /**
     * Create default locale for a marketplace
     */
    @Transactional
    public MarketplaceLocale createDefaultLocale(String marketplaceId, String language, String timezone) {
        MarketplaceLocale locale = new MarketplaceLocale();
        locale.setMarketplaceId(marketplaceId);
        locale.setLanguage(language);
        locale.setTimezone(timezone);
        locale.setDefault(true);
        locale.setActive(true);
        
        // Set default formats based on language
        setDefaultFormats(locale, language);
        
        return localeRepository.save(locale);
    }
    
    /**
     * Add additional locale to marketplace
     */
    @Transactional
    public MarketplaceLocale addLocale(String marketplaceId, String language, String timezone) {
        MarketplaceLocale locale = new MarketplaceLocale();
        locale.setMarketplaceId(marketplaceId);
        locale.setLanguage(language);
        locale.setTimezone(timezone);
        locale.setDefault(false);
        locale.setActive(true);
        
        setDefaultFormats(locale, language);
        
        return localeRepository.save(locale);
    }
    
    /**
     * Get all locales for a marketplace
     */
    @Transactional(readOnly = true)
    public List<MarketplaceLocale> getMarketplaceLocales(String marketplaceId) {
        return localeRepository.findByMarketplaceId(marketplaceId);
    }
    
    /**
     * Get default locale for marketplace
     */
    @Transactional(readOnly = true)
    public MarketplaceLocale getDefaultLocale(String marketplaceId) {
        return localeRepository.findByMarketplaceIdAndIsDefaultTrue(marketplaceId)
            .orElseThrow(() -> new LocaleNotFoundException(
                "No default locale found for marketplace: " + marketplaceId));
    }
    
    /**
     * Set default formats based on language/region
     */
    private void setDefaultFormats(MarketplaceLocale locale, String language) {
        // US formats
        if (language.startsWith("en_US")) {
            locale.setDateFormat("MM/DD/YYYY");
            locale.setTimeFormat("12h");
            locale.setNumberFormat("1,234.56");
        }
        // UK formats
        else if (language.startsWith("en_GB")) {
            locale.setDateFormat("DD/MM/YYYY");
            locale.setTimeFormat("24h");
            locale.setNumberFormat("1,234.56");
        }
        // European formats (DE, FR, etc.)
        else if (language.startsWith("de_") || language.startsWith("fr_")) {
            locale.setDateFormat("DD.MM.YYYY");
            locale.setTimeFormat("24h");
            locale.setNumberFormat("1.234,56");
        }
        // Indian formats
        else if (language.startsWith("hi_IN") || language.startsWith("en_IN")) {
            locale.setDateFormat("DD/MM/YYYY");
            locale.setTimeFormat("12h");
            locale.setNumberFormat("1,23,456.78"); // Indian numbering system
        }
        // Default to US formats
        else {
            locale.setDateFormat("MM/DD/YYYY");
            locale.setTimeFormat("12h");
            locale.setNumberFormat("1,234.56");
        }
    }
    
    public static class LocaleNotFoundException extends RuntimeException {
        public LocaleNotFoundException(String message) {
            super(message);
        }
    }
}
