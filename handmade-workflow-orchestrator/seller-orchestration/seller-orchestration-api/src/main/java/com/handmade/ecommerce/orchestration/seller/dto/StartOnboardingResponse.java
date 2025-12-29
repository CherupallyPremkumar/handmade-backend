package com.handmade.ecommerce.orchestration.seller.dto;

import java.io.Serializable;

/**
 * Public response DTO for the seller onboarding process.
 * Contains the case ID and any immediate verification session details.
 */
public class StartOnboardingResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private String caseId;
    private String identitySessionUrl;
    private String identitySessionId;
    private String identityProvider;
    private String status;
    private String message;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
