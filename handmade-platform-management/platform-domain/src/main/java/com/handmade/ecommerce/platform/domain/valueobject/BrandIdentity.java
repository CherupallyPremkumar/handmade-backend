package com.handmade.ecommerce.platform.domain.valueobject;

import java.io.Serializable;
import java.net.URI;
import java.util.Objects;

/**
 * Value Object representing the visual identity of the platform (White-labeling).
 * Immutable.
 */
public final class BrandIdentity implements Serializable {
    private final URI logoUrl;
    private final URI faviconUrl;
    private final String primaryColorHex;
    private final String supportEmail;

    public BrandIdentity(URI logoUrl, URI faviconUrl, String primaryColorHex, String supportEmail) {
        this.logoUrl = Objects.requireNonNull(logoUrl, "Logo URL cannot be null");
        this.faviconUrl = Objects.requireNonNull(faviconUrl, "Favicon URL cannot be null");
        this.primaryColorHex = Objects.requireNonNull(primaryColorHex, "Primary Color cannot be null");
        this.supportEmail = Objects.requireNonNull(supportEmail, "Support Email cannot be null");
    }

    // Getters
    public URI logoUrl() { return logoUrl; }
    public URI faviconUrl() { return faviconUrl; }
    public String primaryColorHex() { return primaryColorHex; }
    public String supportEmail() { return supportEmail; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandIdentity that = (BrandIdentity) o;
        return logoUrl.equals(that.logoUrl) &&
                faviconUrl.equals(that.faviconUrl) &&
                primaryColorHex.equals(that.primaryColorHex) &&
                supportEmail.equals(that.supportEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logoUrl, faviconUrl, primaryColorHex, supportEmail);
    }
}
