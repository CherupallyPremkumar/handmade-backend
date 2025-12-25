package com.handmade.ecommerce.platform.api.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for publishing legal terms.
 */
public class PublishLegalTermsRequest {
    
    @NotBlank(message = "Operator ID is required")
    private String operatorId;
    
    @NotBlank(message = "Terms URL is required")
    private String termsUrl;
    
    @NotBlank(message = "Privacy URL is required")
    private String privacyUrl;

    public PublishLegalTermsRequest() {}

    public PublishLegalTermsRequest(String operatorId, String termsUrl, String privacyUrl) {
        this.operatorId = operatorId;
        this.termsUrl = termsUrl;
        this.privacyUrl = privacyUrl;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getTermsUrl() {
        return termsUrl;
    }

    public void setTermsUrl(String termsUrl) {
        this.termsUrl = termsUrl;
    }

    public String getPrivacyUrl() {
        return privacyUrl;
    }

    public void setPrivacyUrl(String privacyUrl) {
        this.privacyUrl = privacyUrl;
    }
}
