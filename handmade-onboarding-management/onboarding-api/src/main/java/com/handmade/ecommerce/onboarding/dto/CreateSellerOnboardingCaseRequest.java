package com.handmade.ecommerce.onboarding.dto;

import java.io.Serializable;

public class CreateSellerOnboardingCaseRequest implements Serializable {

    private String email;
    private String country;
    private String sellerType;

    private String policyId;
    private String policyVersion;

    private boolean identityVerificationRequired;
    private boolean taxVerificationRequired;
    private boolean bankVerificationRequired;
    private boolean manualApprovalRequired;

    public CreateSellerOnboardingCaseRequest() {
    }

    public CreateSellerOnboardingCaseRequest(String email, String country, String sellerType, String policyId,
            String policyVersion, boolean identityVerificationRequired, boolean taxVerificationRequired,
            boolean bankVerificationRequired, boolean manualApprovalRequired) {
        this.email = email;
        this.country = country;
        this.sellerType = sellerType;
        this.policyId = policyId;
        this.policyVersion = policyVersion;
        this.identityVerificationRequired = identityVerificationRequired;
        this.taxVerificationRequired = taxVerificationRequired;
        this.bankVerificationRequired = bankVerificationRequired;
        this.manualApprovalRequired = manualApprovalRequired;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSellerType() {
        return sellerType;
    }

    public void setSellerType(String sellerType) {
        this.sellerType = sellerType;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getPolicyVersion() {
        return policyVersion;
    }

    public void setPolicyVersion(String policyVersion) {
        this.policyVersion = policyVersion;
    }

    public boolean isIdentityVerificationRequired() {
        return identityVerificationRequired;
    }

    public void setIdentityVerificationRequired(boolean identityVerificationRequired) {
        this.identityVerificationRequired = identityVerificationRequired;
    }

    public boolean isTaxVerificationRequired() {
        return taxVerificationRequired;
    }

    public void setTaxVerificationRequired(boolean taxVerificationRequired) {
        this.taxVerificationRequired = taxVerificationRequired;
    }

    public boolean isBankVerificationRequired() {
        return bankVerificationRequired;
    }

    public void setBankVerificationRequired(boolean bankVerificationRequired) {
        this.bankVerificationRequired = bankVerificationRequired;
    }

    public boolean isManualApprovalRequired() {
        return manualApprovalRequired;
    }

    public void setManualApprovalRequired(boolean manualApprovalRequired) {
        this.manualApprovalRequired = manualApprovalRequired;
    }
}