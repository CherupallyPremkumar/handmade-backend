package com.handmade.ecommerce.platform.application.command;

/**
 * Command to publish legal terms.
 * Application layer DTO - no business logic.
 */
public class PublishLegalTermsCommand {
    private final String operatorId;
    private final String termsUrl;
    private final String privacyUrl;

    public PublishLegalTermsCommand(String operatorId, String termsUrl, String privacyUrl) {
        this.operatorId = operatorId;
        this.termsUrl = termsUrl;
        this.privacyUrl = privacyUrl;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public String getTermsUrl() {
        return termsUrl;
    }

    public String getPrivacyUrl() {
        return privacyUrl;
    }
}
