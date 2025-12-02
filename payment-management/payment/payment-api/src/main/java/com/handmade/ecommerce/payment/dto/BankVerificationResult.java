package com.handmade.ecommerce.payment.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * DTO for bank verification results
 * Contains verification status, reference ID, and metadata from payment gateway
 */
public class BankVerificationResult implements Serializable {

    private String verificationId;
    private String status; // PENDING, VERIFIED, FAILED, EXPIRED
    private String referenceId; // Reference ID from payment gateway
    private String providerName; // RAZORPAY, STRIPE
    private String accountNumber;
    private String accountHolderName;
    private String bankName;
    private LocalDateTime verifiedAt;
    private String verificationMethod; // PENNY_DROP, MICRO_DEPOSIT, INSTANT
    private Double amountDeposited; // For micro-deposit verification
    private String errorCode;
    private String errorMessage;
    private Map<String, Object> metadata;

    public BankVerificationResult() {
        this.metadata = new HashMap<>();
    }

    // Getters and Setters

    public String getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(String verificationId) {
        this.verificationId = verificationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public String getVerificationMethod() {
        return verificationMethod;
    }

    public void setVerificationMethod(String verificationMethod) {
        this.verificationMethod = verificationMethod;
    }

    public Double getAmountDeposited() {
        return amountDeposited;
    }

    public void setAmountDeposited(Double amountDeposited) {
        this.amountDeposited = amountDeposited;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public void addMetadata(String key, Object value) {
        this.metadata.put(key, value);
    }

    public boolean isVerified() {
        return "VERIFIED".equals(status);
    }

    public boolean isFailed() {
        return "FAILED".equals(status);
    }

    public boolean isPending() {
        return "PENDING".equals(status);
    }

    @Override
    public String toString() {
        return "BankVerificationResult{" +
                "verificationId='" + verificationId + '\'' +
                ", status='" + status + '\'' +
                ", referenceId='" + referenceId + '\'' +
                ", providerName='" + providerName + '\'' +
                ", verifiedAt=" + verifiedAt +
                ", verificationMethod='" + verificationMethod + '\'' +
                '}';
    }
}
