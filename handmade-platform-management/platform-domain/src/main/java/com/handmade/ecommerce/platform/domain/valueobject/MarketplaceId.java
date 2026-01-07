package com.handmade.ecommerce.platform.domain.valueobject;

import java.util.Objects;

/**
 * MarketplaceId Value Object - Type-safe marketplace identifier
 * Examples: "US", "UK", "IN", "DE"
 */
public class MarketplaceId {
    private final String value;

    public MarketplaceId(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Marketplace ID cannot be null or empty");
        }
        if (value.length() > 10) {
            throw new IllegalArgumentException("Marketplace ID cannot exceed 10 characters");
        }
        this.value = value.toUpperCase().trim();
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof MarketplaceId))
            return false;
        MarketplaceId that = (MarketplaceId) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
