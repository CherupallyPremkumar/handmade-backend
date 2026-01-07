package com.handmade.ecommerce.seller.account.dto;

import java.time.Instant;

/**
 * View object for tax verification status
 * Safe for UI consumption (no PII)
 */
public class TaxVerificationView {

    private String sessionId;
    private String onboardingCaseId;

    // Verification status
    private String providerStatus;
    private boolean verified;
    private boolean failed;
    private String failureReason;

    // Tax info (masked for security)
    private String taxIdType;
    private String maskedTaxId; // e.g., "GST***********789"

    // Verification details
    private String verificationStatus; // "ACTIVE", "INACTIVE", etc.

    // Timestamps
    private Instant createdAt;
    private Instant submittedAt;
    private Instant verifiedAt;

    // Constructors
    public TaxVerificationView() {
    }

    // Getters and Setters

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getOnboardingCaseId() {
        return onboardingCaseId;
    }

    public void setOnboardingCaseId(String onboardingCaseId) {
        this.onboardingCaseId = onboardingCaseId;
    }

    public String getProviderStatus() {
        return providerStatus;
    }

    public void setProviderStatus(String providerStatus) {
        this.providerStatus = providerStatus;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isFailed() {
        return failed;
    }

    public void setFailed(boolean failed) {
        this.failed = failed;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public String getTaxIdType() {
        return taxIdType;
    }

    public void setTaxIdType(String taxIdType) {
        this.taxIdType = taxIdType;
    }

    public String getMaskedTaxId() {
        return maskedTaxId;
    }

    public void setMaskedTaxId(String maskedTaxId) {
        this.maskedTaxId = maskedTaxId;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getSubmittedAt() {
        return submittedAt;
    }

    public void setSubmittedAt(Instant submittedAt) {
        this.submittedAt = submittedAt;
    }

    public Instant getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(Instant verifiedAt) {
        this.verifiedAt = verifiedAt;
    }
}
