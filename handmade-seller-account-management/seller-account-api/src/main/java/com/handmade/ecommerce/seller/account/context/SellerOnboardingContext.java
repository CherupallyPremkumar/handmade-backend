package com.handmade.ecommerce.seller.account.context;

import com.handmade.ecommerce.seller.account.dto.StartOnboardingResponse;
import java.io.Serializable;

/**
 * Internal workflow context for seller onboarding orchestration.
 * Stores mutable state shared across orchestration commands.
 */
public class SellerOnboardingContext implements Serializable {
    private static final long serialVersionUID = 1L;

    // Correlation / Request metadata
    private String requestId;

    // Basic Input (copied from Request DTO)
    private String email;
    private String country;
    private String sellerType; // String-based to avoid domain enum leakage

    // Policy Metadata (resolved during orchestration)
    private String policyId;
    private String policyVersion;

    // Workflow State Flags
    private boolean identityVerificationRequired;
    private boolean taxVerificationRequired;
    private boolean bankVerificationRequired;
    private boolean manualApprovalRequired;
    private boolean caseExists;

    // Case Details
    private String caseId;

    // Verification Session details (filled by specific commands)
    private String identitySessionUrl;
    private String identitySessionId;
    private String identityProvider;

    // Final Response DTO
    private StartOnboardingResponse response;

    // Standard Getters and Setters

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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

    public boolean isCaseExists() {
        return caseExists;
    }

    public void setCaseExists(boolean caseExists) {
        this.caseExists = caseExists;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getIdentitySessionUrl() {
        return identitySessionUrl;
    }

    public void setIdentitySessionUrl(String identitySessionUrl) {
        this.identitySessionUrl = identitySessionUrl;
    }

    public String getIdentitySessionId() {
        return identitySessionId;
    }

    public void setIdentitySessionId(String identitySessionId) {
        this.identitySessionId = identitySessionId;
    }

    public String getIdentityProvider() {
        return identityProvider;
    }

    public void setIdentityProvider(String identityProvider) {
        this.identityProvider = identityProvider;
    }

    public StartOnboardingResponse getResponse() {
        return response;
    }

    public void setResponse(StartOnboardingResponse response) {
        this.response = response;
    }

    public boolean isPolicyResolved() {
        return policyId != null && policyVersion != null;
    }
}
