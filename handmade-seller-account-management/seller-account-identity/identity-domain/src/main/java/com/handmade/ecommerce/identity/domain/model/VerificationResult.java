package com.handmade.ecommerce.identity.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Verification result from identity provider (Stripe, Onfido, etc.)
 */
@Embeddable
public class VerificationResult {

    @Column(name = "identity_verified")
    private boolean identityVerified;

    @Column(name = "document_verified")
    private boolean documentVerified;

    @Column(name = "liveness_check_passed")
    private boolean livenessCheckPassed;

    @Column(name = "risk_score")
    private String riskScore; // "LOW", "MEDIUM", "HIGH"

    @Column(name = "failure_reason", length = 500)
    private String failureReason;

    @Column(name = "provider_metadata", length = 2000)
    private String providerMetadata; // JSON string with provider-specific data

    // Constructors
    public VerificationResult() {
    }

    public VerificationResult(boolean identityVerified, boolean documentVerified,
            boolean livenessCheckPassed, String riskScore) {
        this.identityVerified = identityVerified;
        this.documentVerified = documentVerified;
        this.livenessCheckPassed = livenessCheckPassed;
        this.riskScore = riskScore;
    }

    // Getters and Setters

    public boolean isIdentityVerified() {
        return identityVerified;
    }

    public void setIdentityVerified(boolean identityVerified) {
        this.identityVerified = identityVerified;
    }

    public boolean isDocumentVerified() {
        return documentVerified;
    }

    public void setDocumentVerified(boolean documentVerified) {
        this.documentVerified = documentVerified;
    }

    public boolean isLivenessCheckPassed() {
        return livenessCheckPassed;
    }

    public void setLivenessCheckPassed(boolean livenessCheckPassed) {
        this.livenessCheckPassed = livenessCheckPassed;
    }

    public String getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(String riskScore) {
        this.riskScore = riskScore;
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
