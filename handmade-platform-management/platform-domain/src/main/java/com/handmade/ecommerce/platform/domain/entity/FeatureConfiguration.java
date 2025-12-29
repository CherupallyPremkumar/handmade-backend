package com.handmade.ecommerce.platform.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.io.Serializable;

/**
 * Snapshot of what capabilities are currently active on the platform.
 * Immutable Value Object - PURE DOMAIN MODEL.
 */
@Entity
@Table(name = "hm_platform_feature_config")
public class FeatureConfiguration extends BaseJpaEntity implements Serializable {
    private boolean isSellerRegistrationOpen;
    private boolean isCheckoutEnabled;
    private boolean isGuestCheckoutAllowed;

    protected FeatureConfiguration() {} // For framework use

    public FeatureConfiguration(boolean isSellerRegistrationOpen, boolean isCheckoutEnabled, boolean isGuestCheckoutAllowed) {
        this.isSellerRegistrationOpen = isSellerRegistrationOpen;
        this.isCheckoutEnabled = isCheckoutEnabled;
        this.isGuestCheckoutAllowed = isGuestCheckoutAllowed;
    }

    public boolean isSellerRegistrationOpen() { return isSellerRegistrationOpen; }
    public boolean isCheckoutEnabled() { return isCheckoutEnabled; }
    public boolean isGuestCheckoutAllowed() { return isGuestCheckoutAllowed; }
}
