package com.handmade.ecommerce.seller.account.domain.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Tax verification result from tax validation API
 */
@Embeddable
public class TaxVerificationResult {

    @Column(name = "tax_id_verified")
    private boolean taxIdVerified;

    @Column(name = "business_name_matches")
    private boolean businessNameMatches;

    @Column(name = "address_verified")
    private boolean addressVerified;

    @Column(name = "verification_status")
    private String verificationStatus; // "ACTIVE", "INACTIVE", "SUSPENDED", "CANCELLED"

    @Column(name = "tax_failure_reason", length = 500)
    private String failureReason;

    @Column(name = "tax_provider_metadata", length = 2000)
    private String providerMetadata; // JSON string with provider-specific data

    // Constructors
    public TaxVerificationResult() {
    }

    public TaxVerificationResult(boolean taxIdVerified, boolean businessNameMatches,
            boolean addressVerified, String verificationStatus) {
        this.taxIdVerified = taxIdVerified;
        this.businessNameMatches = businessNameMatches;
        this.addressVerified = addressVerified;
        this.verificationStatus = verificationStatus;
    }

    // Getters and Setters

    public boolean isTaxIdVerified() {
        return taxIdVerified;
    }

    public void setTaxIdVerified(boolean taxIdVerified) {
        this.taxIdVerified = taxIdVerified;
    }

    public boolean isBusinessNameMatches() {
        return businessNameMatches;
    }

    public void setBusinessNameMatches(boolean businessNameMatches) {
        this.businessNameMatches = businessNameMatches;
    }

    public boolean isAddressVerified() {
        return addressVerified;
    }

    public void setAddressVerified(boolean addressVerified) {
        this.addressVerified = addressVerified;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getProviderMetadata() {
        return providerMetadata;
    }

    public void setProviderMetadata(String providerMetadata) {
        this.providerMetadata = providerMetadata;
    }
}
