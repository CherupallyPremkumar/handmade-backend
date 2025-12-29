package com.handmade.ecommerce.onboarding.domain.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Bank verification result from Stripe Connect or manual verification
 */
@Embeddable
public class BankVerificationResult {

    @Column(name = "account_verified")
    private boolean accountVerified;

    @Column(name = "name_matches")
    private boolean nameMatches;

    @Column(name = "routing_number_valid")
    private boolean routingNumberValid;

    @Column(name = "bank_verification_method")
    private String verificationMethod; // "MICRO_DEPOSIT", "INSTANT", "MANUAL"

    @Column(name = "bank_failure_reason", length = 500)
    private String failureReason;

    @Column(name = "bank_provider_metadata", length = 2000)
    private String providerMetadata; // JSON string with provider-specific data

    // Constructors
    public BankVerificationResult() {
    }

    public BankVerificationResult(boolean accountVerified, boolean nameMatches,
            boolean routingNumberValid, String verificationMethod) {
        this.accountVerified = accountVerified;
        this.nameMatches = nameMatches;
        this.routingNumberValid = routingNumberValid;
        this.verificationMethod = verificationMethod;
    }

    // Getters and Setters

    public boolean isAccountVerified() {
        return accountVerified;
    }

    public void setAccountVerified(boolean accountVerified) {
        this.accountVerified = accountVerified;
    }

    public boolean isNameMatches() {
        return nameMatches;
    }

    public void setNameMatches(boolean nameMatches) {
        this.nameMatches = nameMatches;
    }

    public boolean isRoutingNumberValid() {
        return routingNumberValid;
    }

    public void setRoutingNumberValid(boolean routingNumberValid) {
        this.routingNumberValid = routingNumberValid;
    }

    public String getVerificationMethod() {
        return verificationMethod;
    }

    public void setVerificationMethod(String verificationMethod) {
        this.verificationMethod = verificationMethod;
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
