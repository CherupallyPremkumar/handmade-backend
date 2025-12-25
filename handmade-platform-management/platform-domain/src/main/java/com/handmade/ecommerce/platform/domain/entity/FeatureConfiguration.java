package com.handmade.ecommerce.platform.domain.entity;

import java.io.Serializable;

/**
 * Snapshot of what capabilities are currently active on the platform.
 * Immutable Value Object - PURE DOMAIN MODEL.
 */
public class FeatureConfiguration implements Serializable {
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
