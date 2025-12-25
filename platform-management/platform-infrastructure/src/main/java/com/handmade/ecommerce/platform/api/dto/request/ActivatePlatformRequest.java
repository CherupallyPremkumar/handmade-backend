package com.handmade.ecommerce.platform.api.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for activating the platform.
 */
public class ActivatePlatformRequest {
    
    @NotBlank(message = "Operator ID is required")
    private String operatorId;
    
    private String reason;

    public ActivatePlatformRequest() {}

    public ActivatePlatformRequest(String operatorId, String reason) {
        this.operatorId = operatorId;
        this.reason = reason;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
