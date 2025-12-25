package com.handmade.ecommerce.platform.domain.valueobject;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Regulatory rules that must be enforced.
 * Immutable Value Object - PURE DOMAIN MODEL.
 */
public class ComplianceMandate implements Serializable {
    private boolean requiresSellerKYC;
    private boolean requiresTaxId;
    private List<String> allowedJurisdictions; // Country Codes

    protected ComplianceMandate() {}

    public ComplianceMandate(boolean requiresSellerKYC, boolean requiresTaxId, List<String> allowedJurisdictions) {
        this.requiresSellerKYC = requiresSellerKYC;
        this.requiresTaxId = requiresTaxId;
        this.allowedJurisdictions = allowedJurisdictions != null ? List.copyOf(allowedJurisdictions) : Collections.emptyList();
    }

    public boolean isRequiresSellerKYC() { return requiresSellerKYC; }
    public boolean isRequiresTaxId() { return requiresTaxId; }
    public List<String> getAllowedJurisdictions() { return allowedJurisdictions; }
}
