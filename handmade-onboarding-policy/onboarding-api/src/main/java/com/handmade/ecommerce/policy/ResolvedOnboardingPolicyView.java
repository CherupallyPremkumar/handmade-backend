package com.handmade.ecommerce.policy;

import com.handmade.ecommerce.platform.domain.enums.SellerType;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Read-only view returned by onboarding-policy-management
 * Represents a resolved onboarding policy snapshot.
 *
 * Immutable, versioned, and safe for cross-service use.
 */
public final class ResolvedOnboardingPolicyView implements Serializable {

    private static final long serialVersionUID = 1L;

    // Policy identity
    private final String policyId;
    private final String version;

    // Applicability (echoed for traceability)
    private final String country;
    private final SellerType sellerType;
    private final LocalDate effectiveDate;

    // Requirements
    private final boolean identityVerificationRequired;
    private final boolean taxVerificationRequired;
    private final boolean bankVerificationRequired;
    private final boolean manualApprovalRequired;

    // Optional: SLA metadata (Amazon-grade)
    private final Integer identitySlaDays;
    private final Integer taxSlaDays;
    private final Integer bankSlaDays;

    private ResolvedOnboardingPolicyView(Builder builder) {
        this.policyId = builder.policyId;
        this.version = builder.version;
        this.country = builder.country;
        this.sellerType = builder.sellerType;
        this.effectiveDate = builder.effectiveDate;
        this.identityVerificationRequired = builder.identityVerificationRequired;
        this.taxVerificationRequired = builder.taxVerificationRequired;
        this.bankVerificationRequired = builder.bankVerificationRequired;
        this.manualApprovalRequired = builder.manualApprovalRequired;
        this.identitySlaDays = builder.identitySlaDays;
        this.taxSlaDays = builder.taxSlaDays;
        this.bankSlaDays = builder.bankSlaDays;
    }

    public String getPolicyId() {
        return policyId;
    }

    public String getVersion() {
        return version;
    }

    public String getCountry() {
        return country;
    }

    public SellerType getSellerType() {
        return sellerType;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public boolean isIdentityVerificationRequired() {
        return identityVerificationRequired;
    }

    public boolean isTaxVerificationRequired() {
        return taxVerificationRequired;
    }

    public boolean isBankVerificationRequired() {
        return bankVerificationRequired;
    }

    public boolean isManualApprovalRequired() {
        return manualApprovalRequired;
    }

    public Integer getIdentitySlaDays() {
        return identitySlaDays;
    }

    public Integer getTaxSlaDays() {
        return taxSlaDays;
    }

    public Integer getBankSlaDays() {
        return bankSlaDays;
    }

    // --------------------
    // Builder
    // --------------------
    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String policyId;
        private String version;
        private String country;
        private SellerType sellerType;
        private LocalDate effectiveDate;

        private boolean identityVerificationRequired;
        private boolean taxVerificationRequired;
        private boolean bankVerificationRequired;
        private boolean manualApprovalRequired;

        private Integer identitySlaDays;
        private Integer taxSlaDays;
        private Integer bankSlaDays;

        private Builder() {}

        public Builder policyId(String policyId) {
            this.policyId = policyId;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder country(String country) {
            this.country = country;
            return this;
        }

        public Builder sellerType(SellerType sellerType) {
            this.sellerType = sellerType;
            return this;
        }

        public Builder effectiveDate(LocalDate effectiveDate) {
            this.effectiveDate = effectiveDate;
            return this;
        }

        public Builder identityVerificationRequired(boolean value) {
            this.identityVerificationRequired = value;
            return this;
        }

        public Builder taxVerificationRequired(boolean value) {
            this.taxVerificationRequired = value;
            return this;
        }

        public Builder bankVerificationRequired(boolean value) {
            this.bankVerificationRequired = value;
            return this;
        }

        public Builder manualApprovalRequired(boolean value) {
            this.manualApprovalRequired = value;
            return this;
        }

        public Builder identitySlaDays(Integer days) {
            this.identitySlaDays = days;
            return this;
        }

        public Builder taxSlaDays(Integer days) {
            this.taxSlaDays = days;
            return this;
        }

        public Builder bankSlaDays(Integer days) {
            this.bankSlaDays = days;
            return this;
        }

        public ResolvedOnboardingPolicyView build() {
            return new ResolvedOnboardingPolicyView(this);
        }
    }
}