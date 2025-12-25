package com.handmade.ecommerce.platform.application.command;

import java.net.URI;

/**
 * Command to update brand identity.
 * Application layer DTO - no business logic.
 */
public class UpdateBrandIdentityCommand {
    private final String operatorId;
    private final URI logoUrl;
    private final String primaryColor;
    private final String supportEmail;

    public UpdateBrandIdentityCommand(String operatorId, URI logoUrl, 
                                     String primaryColor, String supportEmail) {
        this.operatorId = operatorId;
        this.logoUrl = logoUrl;
        this.primaryColor = primaryColor;
        this.supportEmail = supportEmail;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public URI getLogoUrl() {
        return logoUrl;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public String getSupportEmail() {
        return supportEmail;
    }
}
