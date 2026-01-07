package com.handmade.ecommerce.platform.domain.aggregate;

import org.chenile.jpautils.entity.AbstractJpaStateEntity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Marketplace Aggregate Root
 * Represents a regional marketplace (e.g., US, UK, India)
 * 
 * State Machine: DRAFT → ACTIVE → SUSPENDED
 * 
 * Governed by PlatformOwner but NOT owned (separate aggregate)
 */
@Entity
@Table(name = "hm_platform_marketplaces", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "country_code" })
})
public class Marketplace extends AbstractJpaStateEntity {

    /**
     * Marketplace identifier (e.g., "US", "UK", "IN")
     * This is also the primary key
     */
    @Column(name = "marketplace_id", length = 10, nullable = false, unique = true)
    private String marketplaceId;

    /**
     * Display name (e.g., "United States", "United Kingdom")
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * ISO 3166-1 alpha-2 country code
     */
    @Column(name = "country_code", length = 2, nullable = false)
    private String countryCode;

    /**
     * Primary currency for this marketplace (ISO 4217)
     */
    @Column(name = "currency", length = 3, nullable = false)
    private String currency;

    /**
     * Default language (e.g., "en_US", "en_GB", "hi_IN")
     */
    @Column(name = "default_language", length = 10)
    private String defaultLanguage;

    /**
     * Default timezone (e.g., "America/New_York", "Europe/London")
     */
    @Column(name = "default_timezone", length = 50)
    private String defaultTimezone;

    /**
     * Reference to PlatformOwner (for authorization, not ownership)
     */
    @Column(name = "platform_owner_id", nullable = false)
    private String platformOwnerId;

    /**
     * Who created this marketplace
     */
    @Column(name = "created_by")
    private String createdBy;

    /**
     * When marketplace was created
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * When marketplace was activated
     */
    @Column(name = "activated_at")
    private LocalDateTime activatedAt;

    /**
     * Who activated this marketplace
     */
    @Column(name = "activated_by")
    private String activatedBy;

    /**
     * Suspension tracking
     */
    @Column(name = "suspended_at")
    private LocalDateTime suspendedAt;

    @Column(name = "suspended_by")
    private String suspendedBy;

    @Column(name = "suspension_reason")
    private String suspensionReason;

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    /**
     * Activate marketplace
     */
    public void activate(String activatedBy) {
        this.activatedAt = LocalDateTime.now();
        this.activatedBy = activatedBy;
    }

    /**
     * Suspend marketplace
     */
    public void suspend(String suspendedBy, String reason) {
        this.suspendedAt = LocalDateTime.now();
        this.suspendedBy = suspendedBy;
        this.suspensionReason = reason;
    }

    /**
     * Check if marketplace is active
     */
    public boolean isActive() {
        return "ACTIVE".equals(getCurrentState().getStateId());
    }

    @Override
    protected String getPrefix() {
        return "MKT";
    }

    // Getters and Setters
    public String getMarketplaceId() {
        return marketplaceId;
    }

    public void setMarketplaceId(String marketplaceId) {
        this.marketplaceId = marketplaceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }

    public void setDefaultLanguage(String defaultLanguage) {
        this.defaultLanguage = defaultLanguage;
    }

    public String getDefaultTimezone() {
        return defaultTimezone;
    }

    public void setDefaultTimezone(String defaultTimezone) {
        this.defaultTimezone = defaultTimezone;
    }

    public String getPlatformOwnerId() {
        return platformOwnerId;
    }

    public void setPlatformOwnerId(String platformOwnerId) {
        this.platformOwnerId = platformOwnerId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getActivatedAt() {
        return activatedAt;
    }

    public String getActivatedBy() {
        return activatedBy;
    }

    public LocalDateTime getSuspendedAt() {
        return suspendedAt;
    }

    public String getSuspendedBy() {
        return suspendedBy;
    }

    public String getSuspensionReason() {
        return suspensionReason;
    }
}
