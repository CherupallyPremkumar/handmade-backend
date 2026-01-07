package com.handmade.ecommerce.identity.dto;

/**
 * Response from external identity verification session creation
 */
public class VerificationSessionResponse {

    private String sessionId;
    private String clientSecret;
    private String url;

    public VerificationSessionResponse() {
    }

    public VerificationSessionResponse(String sessionId, String clientSecret, String url) {
        this.sessionId = sessionId;
        this.clientSecret = clientSecret;
        this.url = url;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
