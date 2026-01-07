package com.handmade.ecommerce.platform.domain.entity;

import org.chenile.jpautils.entity.BaseJpaEntity;

import jakarta.persistence.*;

/**
 * MarketplaceLocale Entity
 * Represents locale-specific configuration for a marketplace
 * 
 * Example: US marketplace might have:
 * - en_US locale (English - United States)
 * - es_US locale (Spanish - United States)
 */
@Entity
@Table(name = "hm_platform_marketplace_locales", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "marketplace_id", "language" })
})
public class MarketplaceLocale extends BaseJpaEntity {

    /**
     * Reference to parent Marketplace
     */
    @Column(name = "marketplace_id", length = 10, nullable = false)
    private String marketplaceId;

    /**
     * Language code (e.g., "en_US", "en_GB", "hi_IN", "es_US")
     */
    @Column(name = "language", length = 10, nullable = false)
    private String language;

    /**
     * Timezone for this locale (e.g., "America/New_York")
     */
    @Column(name = "timezone", length = 50)
    private String timezone;

    /**
     * Date format (e.g., "MM/DD/YYYY", "DD/MM/YYYY")
     */
    @Column(name = "date_format", length = 20)
    private String dateFormat;

    /**
     * Time format (e.g., "12h", "24h")
     */
    @Column(name = "time_format", length = 10)
    private String timeFormat;

    /**
     * Number format (e.g., "1,234.56" vs "1.234,56")
     */
    @Column(name = "number_format", length = 20)
    private String numberFormat;

    /**
     * Whether this is the default locale for the marketplace
     */
    @Column(name = "is_default")
    private boolean isDefault = false;

    /**
     * Whether this locale is active
     */
    @Column(name = "active")
    private boolean active = true;

    // Getters and Setters
    public String getMarketplaceId() {
        return marketplaceId;
    }

    public void setMarketplaceId(String marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getNumberFormat() {
        return numberFormat;
    }

    public void setNumberFormat(String numberFormat) {
        this.numberFormat = numberFormat;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
