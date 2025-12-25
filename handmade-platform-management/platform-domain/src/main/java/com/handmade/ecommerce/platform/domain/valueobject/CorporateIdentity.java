package com.handmade.ecommerce.platform.domain.valueobject;

import java.io.Serializable;
import java.util.Objects;

/**
 * Value Object representing the legal corporate identity behind the platform.
 * Used for invoicing and legal compliance.
 * Immutable.
 */
public final class CorporateIdentity implements Serializable {
    private final String legalBusinessName;
    private final String taxRegistrationId;
    private final String registeredAddress; // Simplified for this example, could be Address VO
    private final String supportPhoneNumber;

    public CorporateIdentity(String legalBusinessName, String taxRegistrationId, String registeredAddress, String supportPhoneNumber) {
        this.legalBusinessName = Objects.requireNonNull(legalBusinessName, "Legal Name cannot be null");
        this.taxRegistrationId = Objects.requireNonNull(taxRegistrationId, "Tax ID cannot be null");
        this.registeredAddress = Objects.requireNonNull(registeredAddress, "Address cannot be null");
        this.supportPhoneNumber = Objects.requireNonNull(supportPhoneNumber, "Phone cannot be null");
    }

    public String legalBusinessName() { return legalBusinessName; }
    public String taxRegistrationId() { return taxRegistrationId; }
    public String registeredAddress() { return registeredAddress; }
    public String supportPhoneNumber() { return supportPhoneNumber; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CorporateIdentity that = (CorporateIdentity) o;
        return legalBusinessName.equals(that.legalBusinessName) &&
                taxRegistrationId.equals(that.taxRegistrationId) &&
                registeredAddress.equals(that.registeredAddress) &&
                supportPhoneNumber.equals(that.supportPhoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(legalBusinessName, taxRegistrationId, registeredAddress, supportPhoneNumber);
    }
}
