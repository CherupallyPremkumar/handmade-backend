package com.handmade.ecommerce.identity.dto;

import java.time.Instant;

/**
 * View object for identity verification status
 * Safe for UI consumption (no PII)
 */
public class IdentityVerificationView {

    private String sessionId;
    private String onboardingCaseId;

    // Stripe integration (for frontend)
    private String externalClientSecret;
    private String externalUrl;
    private String externalStatus;

    // Verification status
    private boolean verified;
    private boolean failed;
    private String failureReason;

    // Risk assessment (admin only)
    private String riskScore;

    // Timestamps
    private Instant createdAt;
    private Instant submittedAt;
    private Instant verifiedAt;

    // Constructors
    public IdentityVerificationView() {
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

    public String getExternalClientSecret() {
        return externalClientSecret;
    }

    public void setExternalClientSecret(String externalClientSecret) {
        this.externalClientSecret = externalClientSecret;
    }

    public String getExternalUrl() {
        return externalUrl;
    }

    public void setExternalUrl(String externalUrl) {
        this.externalUrl = externalUrl;
    }

    public String getExternalStatus() {
        return externalStatus;
    }

    public void setExternalStatus(String externalStatus) {
        this.externalStatus = externalStatus;
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

    public String getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(String riskScore) {
        this.riskScore = riskScore;
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
