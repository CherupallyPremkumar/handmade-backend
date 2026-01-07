package com.handmade.ecommerce.seller.account.service.tax;

import com.handmade.ecommerce.seller.account.domain.valueobject.TaxInformation;
import com.handmade.ecommerce.seller.account.domain.valueobject.TaxVerificationResult;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.time.Instant;

/**
 * Tax Verification Session
 * 
 * Simple JPA entity (NOT STM) for tracking tax verification.
 * State is managed by external tax validation APIs.
 * Workflow state is managed by SellerAccount (STM).
 */
@Entity
@Table(name = "tax_verification_session")
public class TaxVerificationSession extends BaseJpaEntity {

    // Link to onboarding case
    @Column(name = "onboarding_case_id", nullable = false)
    private String onboardingCaseId;

    @Column(name = "seller_email", nullable = false)
    private String sellerEmail;

    // Tax Information (PII - should be encrypted)
    @Embedded
    private TaxInformation taxInformation;

    // Provider Integration (e.g., GST API, VIES, Avalara)
    @Column(name = "provider_session_id")
    private String providerSessionId;

    @Column(name = "provider_status")
    private String providerStatus; // "ACTIVE", "INACTIVE", "PENDING", "VERIFIED"

    // Verification Result
    @Embedded
    private TaxVerificationResult result;

    // Document URLs (S3 or storage service)
    @Column(name = "tax_certificate_url", length = 500)
    private String taxCertificateUrl;

    @Column(name = "gst_certificate_url", length = 500)
    private String gstCertificateUrl;

    // Timestamps
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "submitted_at")
    private Instant submittedAt;

    @Column(name = "verified_at")
    private Instant verifiedAt;

    // Constructor
    public TaxVerificationSession() {
        this.createdAt = Instant.now();
        this.providerStatus = "pending";
    }

    // Business Methods (NO state transitions - just data updates)

    /**
     * Update tax information
     * Can be called multiple times (idempotent)
     */
    public void updateTaxInformation(TaxInformation taxInfo) {
        this.taxInformation = taxInfo;
        if (this.submittedAt == null) {
            this.submittedAt = Instant.now();
        }
    }

    /**
     * Record provider verification result
     */
    public void recordVerificationResult(String status, TaxVerificationResult result) {
        this.providerStatus = status;
        this.result = result;

        if ("ACTIVE".equals(status) || "VERIFIED".equals(status)) {
            this.verifiedAt = Instant.now();
        }
    }

    /**
     * Update document URLs
     */
    public void updateDocuments(String taxCertUrl, String gstCertUrl) {
        this.taxCertificateUrl = taxCertUrl;
        this.gstCertificateUrl = gstCertUrl;
    }

    /**
     * Check if ready for verification
     */
    public boolean isReadyForVerification() {
        return this.taxInformation != null &&
                this.taxInformation.getTaxIdNumber() != null;
    }

    /**
     * Check if verification is complete
     */
    public boolean isVerified() {
        return "ACTIVE".equals(this.providerStatus) ||
                "VERIFIED".equals(this.providerStatus);
    }

    /**
     * Check if verification failed
     */
    public boolean isFailed() {
        return "INACTIVE".equals(this.providerStatus) ||
                "SUSPENDED".equals(this.providerStatus) ||
                "CANCELLED".equals(this.providerStatus) ||
                (this.result != null && !this.result.isTaxIdVerified());
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

    public TaxInformation getTaxInformation() {
        return taxInformation;
    }

    public void setTaxInformation(TaxInformation taxInformation) {
        this.taxInformation = taxInformation;
    }

    public String getProviderSessionId() {
        return providerSessionId;
    }

    public void setProviderSessionId(String providerSessionId) {
        this.providerSessionId = providerSessionId;
    }

    public String getProviderStatus() {
        return providerStatus;
    }

    public void setProviderStatus(String providerStatus) {
        this.providerStatus = providerStatus;
    }

    public TaxVerificationResult getResult() {
        return result;
    }

    public void setResult(TaxVerificationResult result) {
        this.result = result;
    }

    public String getTaxCertificateUrl() {
        return taxCertificateUrl;
    }

    public void setTaxCertificateUrl(String taxCertificateUrl) {
        this.taxCertificateUrl = taxCertificateUrl;
    }

    public String getGstCertificateUrl() {
        return gstCertificateUrl;
    }

    public void setGstCertificateUrl(String gstCertificateUrl) {
        this.gstCertificateUrl = gstCertificateUrl;
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
