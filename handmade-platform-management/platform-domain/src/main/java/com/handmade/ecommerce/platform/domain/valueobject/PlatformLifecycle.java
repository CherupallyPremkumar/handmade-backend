package com.handmade.ecommerce.platform.domain.valueobject;

/**
 * Represents the operational lifecycle state of the Platform.
 * Governs what operations are permitted globally.
 */
public enum PlatformLifecycle {
    /**
     * Initial state. Platform is being configured but not yet open to public.
     */
    PROVISIONING,

    /**
     * Fully operational state. All systems active.
     */
    LIVE,

    /**
     * Restricted operations. E.g., No new seller registrations, or regulatory freeze.
     * Existing transactions may continue.
     */
    RESTRICTED,

    /**
     * Planned technical degradation.
     * User-facing systems may be read-only or offline.
     */
    MAINTENANCE_MODE,

    /**
     * End of life. Read-only access for archival purposes only.
     */
    SUNSET;

    public boolean isOperational() {
        return this == LIVE || this == RESTRICTED;
    }

    public boolean allowsWrites() {
        return this != MAINTENANCE_MODE && this != SUNSET;
    }
}
