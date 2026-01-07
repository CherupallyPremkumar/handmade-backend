package com.handmade.ecommerce.seller.account.service.bank;

import com.handmade.ecommerce.seller.account.domain.valueobject.BankAccountInfo;
import com.handmade.ecommerce.seller.account.domain.valueobject.BankVerificationResult;
import jakarta.persistence.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

import java.time.Instant;

/**
 * Bank Verification Session
 * 
 * Simple JPA entity (NOT STM) for tracking bank account verification.
 * State is managed by an external provider (e.g., Stripe Connect).
 * Workflow state is managed by SellerAccount (STM).
 */
@Entity
@Table(name = "bank_verification_session")
public class BankVerificationSession extends BaseJpaEntity {

    // Link to onboarding case
    @Column(name = "onboarding_case_id", nullable = false)
    private String onboardingCaseId;

    @Column(name = "seller_email", nullable = false)
    private String sellerEmail;

    // Bank Account Info (PII - should be encrypted)
    @Embedded
    private BankAccountInfo bankAccountInfo;

    // External Provider Integration (e.g., Stripe Connect)
    @Column(name = "external_account_id")
    private String externalAccountId; // External provider account ID

    @Column(name = "external_bank_account_id")
    private String externalBankAccountId; // External bank account ID

    @Column(name = "external_status")
    private String externalStatus; // "new", "verified", "verification_failed", "errored"

    // Verification Method
    @Column(name = "verification_method")
    private String verificationMethod; // "MICRO_DEPOSIT", "INSTANT", "MANUAL"

    // Micro-deposit verification
    @Column(name = "micro_deposit_1")
    private Integer microDeposit1; // In cents (for verification)

    @Column(name = "micro_deposit_2")
    private Integer microDeposit2; // In cents (for verification)

    @Column(name = "verification_attempts")
    private Integer verificationAttempts;

    @Column(name = "max_verification_attempts")
    private Integer maxVerificationAttempts;

    // Verification Result
    @Embedded
    private BankVerificationResult result;

    // Timestamps
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "submitted_at")
    private Instant submittedAt;

    @Column(name = "verified_at")
    private Instant verifiedAt;

    // Constructor
    public BankVerificationSession() {
        this.createdAt = Instant.now();
        this.externalStatus = "new";
        this.verificationAttempts = 0;
        this.maxVerificationAttempts = 3;
    }

    // Business Methods (NO state transitions - just data updates)

    /**
     * Update bank account information
     * Can be called multiple times (idempotent)
     */
    public void updateBankAccountInfo(BankAccountInfo bankInfo) {
        this.bankAccountInfo = bankInfo;
        if (this.submittedAt == null) {
            this.submittedAt = Instant.now();
        }
    }

    /**
     * Record external account creation
     */
    public void recordExternalAccount(String accountId, String bankAccountId) {
        this.externalAccountId = accountId;
        this.externalBankAccountId = bankAccountId;
    }

    /**
     * Record micro-deposit initiation
     */
    public void recordMicroDeposits(int amount1, int amount2) {
        this.microDeposit1 = amount1;
        this.microDeposit2 = amount2;
        this.verificationMethod = "MICRO_DEPOSIT";
    }

    /**
     * Verify micro-deposit amounts
     */
    public boolean verifyMicroDeposits(int amount1, int amount2) {
        if (this.verificationAttempts >= this.maxVerificationAttempts) {
            return false;
        }

        this.verificationAttempts++;

        if (this.microDeposit1 != null && this.microDeposit2 != null &&
                this.microDeposit1 == amount1 && this.microDeposit2 == amount2) {
            this.externalStatus = "verified";
            this.verifiedAt = Instant.now();
            return true;
        }

        if (this.verificationAttempts >= this.maxVerificationAttempts) {
            this.externalStatus = "verification_failed";
        }

        return false;
    }

    /**
     * Record verification result from external provider
     */
    public void recordVerificationResult(String status, BankVerificationResult result) {
        this.externalStatus = status;
        this.result = result;

        if ("verified".equals(status)) {
            this.verifiedAt = Instant.now();
        }
    }

    /**
     * Check if ready for verification
     */
    public boolean isReadyForVerification() {
        return this.bankAccountInfo != null &&
                this.externalAccountId != null &&
                this.externalBankAccountId != null;
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
        return "verification_failed".equals(this.externalStatus) ||
                "errored".equals(this.externalStatus) ||
                (this.result != null && !this.result.isAccountVerified());
    }

    /**
     * Check if micro-deposits were initiated
     */
    public boolean hasMicroDeposits() {
        return this.microDeposit1 != null && this.microDeposit2 != null;
    }

    /**
     * Get remaining verification attempts
     */
    public int getRemainingAttempts() {
        return Math.max(0, this.maxVerificationAttempts - this.verificationAttempts);
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

    public BankAccountInfo getBankAccountInfo() {
        return bankAccountInfo;
    }

    public void setBankAccountInfo(BankAccountInfo bankAccountInfo) {
        this.bankAccountInfo = bankAccountInfo;
    }

    public String getExternalAccountId() {
        return externalAccountId;
    }

    public void setExternalAccountId(String externalAccountId) {
        this.externalAccountId = externalAccountId;
    }

    public String getExternalBankAccountId() {
        return externalBankAccountId;
    }

    public void setExternalBankAccountId(String externalBankAccountId) {
        this.externalBankAccountId = externalBankAccountId;
    }

    public String getExternalStatus() {
        return externalStatus;
    }

    public void setExternalStatus(String externalStatus) {
        this.externalStatus = externalStatus;
    }

    public String getVerificationMethod() {
        return verificationMethod;
    }

    public void setVerificationMethod(String verificationMethod) {
        this.verificationMethod = verificationMethod;
    }

    public Integer getMicroDeposit1() {
        return microDeposit1;
    }

    public void setMicroDeposit1(Integer microDeposit1) {
        this.microDeposit1 = microDeposit1;
    }

    public Integer getMicroDeposit2() {
        return microDeposit2;
    }

    public void setMicroDeposit2(Integer microDeposit2) {
        this.microDeposit2 = microDeposit2;
    }

    public Integer getVerificationAttempts() {
        return verificationAttempts;
    }

    public void setVerificationAttempts(Integer verificationAttempts) {
        this.verificationAttempts = verificationAttempts;
    }

    public Integer getMaxVerificationAttempts() {
        return maxVerificationAttempts;
    }

    public void setMaxVerificationAttempts(Integer maxVerificationAttempts) {
        this.maxVerificationAttempts = maxVerificationAttempts;
    }

    public BankVerificationResult getResult() {
        return result;
    }

    public void setResult(BankVerificationResult result) {
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
