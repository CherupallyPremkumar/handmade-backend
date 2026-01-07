package com.handmade.ecommerce.identity.domain.model;

import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.time.Instant;

/**
 * Identity Verification Session
 * 
 * Simple JPA entity (NOT STM) for tracking external identity verification.
 * State is managed by an external provider (e.g., Stripe) and mirrored here.
 * Workflow state is managed by SellerAccount (STM).
 */
@Entity
@Table(name = "identity_verification_session")
public class IdentityVerificationSession extends BaseJpaEntity {

    // Link to onboarding case
    @Column(name = "onboarding_case_id", nullable = false)
    private String onboardingCaseId;

    @Column(name = "seller_email", nullable = false)
    private String sellerEmail;

    // Personal Identity (PII - should be encrypted)
    @Embedded
    private PersonalIdentity personalIdentity;

    // External Provider Integration (e.g., Stripe)
    @Column(name = "external_session_id")
    private String externalSessionId;

    @Column(name = "external_verification_id")
    private String externalVerificationId;

    @Column(name = "external_status")
    private String externalStatus; // Mirror provider's status: processing, verified, requires_input, canceled

    @Column(name = "external_client_secret", length = 500)
    private String externalClientSecret; // For frontend integration

    @Column(name = "external_url", length = 500)
    private String externalUrl; // Hosted verification URL

    // Verification Result
    @Embedded
    private VerificationResult result;

    // Timestamps
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "submitted_at")
    private Instant submittedAt;

    @Column(name = "verified_at")
    private Instant verifiedAt;

    // Constructor
    public IdentityVerificationSession() {
        this.createdAt = Instant.now();
        this.externalStatus = "pending";
    }

    // Business Methods (NO state transitions - just data updates)

    /**
     * Update personal identity information
     * Can be called multiple times (idempotent)
     */
    public void updatePersonalIdentity(PersonalIdentity identity) {
        this.personalIdentity = identity;
        if (this.submittedAt == null) {
            this.submittedAt = Instant.now();
        }
    }

    /**
     * Record external verification session creation
     */
    public void recordExternalVerificationSession(String sessionId, String clientSecret, String url) {
        if (this.externalSessionId != null) {
            throw new IllegalStateException("Verification session already created");
        }
        this.externalSessionId = sessionId;
        this.externalClientSecret = clientSecret;
        this.externalUrl = url;
        this.externalStatus = "processing";
    }

    /**
     * Update from external provider webhook
     * Idempotent - can be called multiple times with same result
     */
    public void updateFromExternalWebhook(String status, VerificationResult result) {
        this.externalStatus = status;
        this.result = result;

        if ("verified".equals(status)) {
            this.verifiedAt = Instant.now();
        }
    }

    /**
     * Check if ready for external verification session creation
     */
    public boolean isReadyForExternalSession() {
        return this.personalIdentity != null && 
               this.externalSessionId == null && 
               !"verified".equals(this.externalStatus) &&
               !"processing".equals(this.externalStatus);
    }

    /**
     * Check if verification is complete
     */
    public boolean isVerified() {
        return "verified".equals(this.externalStatus);
    }

    /**
     * Check if verification failed
     */
    public boolean isFailed() {
        return "canceled".equals(this.externalStatus) ||
                (this.result != null && !this.result.isIdentityVerified());
    }

    // Getters and Setters

    public String getOnboardingCaseId() {
        return onboardingCaseId;
    }

    public void setOnboardingCaseId(String onboardingCaseId) {
        this.onboardingCaseId = onboardingCaseId;
    }

    public String getSellerEmail() {
        return sellerEmail;
    }

    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail;
    }

    public PersonalIdentity getPersonalIdentity() {
        return personalIdentity;
    }

    public void setPersonalIdentity(PersonalIdentity personalIdentity) {
        this.personalIdentity = personalIdentity;
    }

    public String getExternalSessionId() {
        return externalSessionId;
    }

    public void setExternalSessionId(String externalSessionId) {
        this.externalSessionId = externalSessionId;
    }

    public String getExternalVerificationId() {
        return externalVerificationId;
    }

    public void setExternalVerificationId(String externalVerificationId) {
        this.externalVerificationId = externalVerificationId;
    }

    public String getExternalStatus() {
        return externalStatus;
    }

    public void setExternalStatus(String externalStatus) {
        this.externalStatus = externalStatus;
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

    public VerificationResult getResult() {
        return result;
    }

    public void setResult(VerificationResult result) {
        this.result = result;
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
