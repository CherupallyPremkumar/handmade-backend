package com.handmade.ecommerce.platform.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Request DTO for updating brand identity.
 */
public class UpdateBrandIdentityRequest {
    
    @NotBlank(message = "Operator ID is required")
    private String operatorId;
    
    @NotBlank(message = "Logo URL is required")
    private String logoUrl;
    
    @Pattern(regexp = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$", message = "Invalid hex color format")
    private String primaryColor;
    
    @NotBlank(message = "Support email is required")
    @Email(message = "Invalid email format")
    private String supportEmail;

    public UpdateBrandIdentityRequest() {}

    public UpdateBrandIdentityRequest(String operatorId, String logoUrl, String primaryColor, String supportEmail) {
        this.operatorId = operatorId;
        this.logoUrl = logoUrl;
        this.primaryColor = primaryColor;
        this.supportEmail = supportEmail;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public String getSupportEmail() {
        return supportEmail;
    }

    public void setSupportEmail(String supportEmail) {
        this.supportEmail = supportEmail;
    }
}
