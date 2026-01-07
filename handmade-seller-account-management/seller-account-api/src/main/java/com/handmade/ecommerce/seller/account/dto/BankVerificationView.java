package com.handmade.ecommerce.seller.account.dto;

import java.time.Instant;

/**
 * View object for bank verification status
 * Safe for UI consumption (no sensitive bank details)
 */
public class BankVerificationView {

    private String sessionId;
    private String onboardingCaseId;

    // Bank info (masked for security)
    private String accountHolderName;
    private String accountType;
    private String maskedAccountNumber; // e.g., "****1234"
    private String bankName;
    private String currency;

    // Verification status
    private String stripeStatus;
    private String verificationMethod;
    private boolean verified;
    private boolean failed;
    private String failureReason;

    // Micro-deposit info (if applicable)
    private boolean microDepositsInitiated;
    private int remainingAttempts;

    // Timestamps
    private Instant createdAt;
    private Instant submittedAt;
    private Instant verifiedAt;

    // Constructors
    public BankVerificationView() {
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

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getMaskedAccountNumber() {
        return maskedAccountNumber;
    }

    public void setMaskedAccountNumber(String maskedAccountNumber) {
        this.maskedAccountNumber = maskedAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStripeStatus() {
        return stripeStatus;
    }

    public void setStripeStatus(String stripeStatus) {
        this.stripeStatus = stripeStatus;
    }

    public String getVerificationMethod() {
        return verificationMethod;
    }

    public void setVerificationMethod(String verificationMethod) {
        this.verificationMethod = verificationMethod;
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

    public boolean isMicroDepositsInitiated() {
        return microDepositsInitiated;
    }

    public void setMicroDepositsInitiated(boolean microDepositsInitiated) {
        this.microDepositsInitiated = microDepositsInitiated;
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public void setRemainingAttempts(int remainingAttempts) {
        this.remainingAttempts = remainingAttempts;
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
