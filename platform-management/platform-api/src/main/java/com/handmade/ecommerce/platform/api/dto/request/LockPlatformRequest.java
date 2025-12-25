package com.handmade.ecommerce.platform.api.dto.request;

import jakarta.validation.constraints.NotBlank;

/**
 * Request DTO for locking/restricting the platform.
 */
public class LockPlatformRequest {
    
    @NotBlank(message = "Operator ID is required")
    private String operatorId;
    
    @NotBlank(message = "Reason is required for platform lockdown")
    private String reason;

    public LockPlatformRequest() {}

    public LockPlatformRequest(String operatorId, String reason) {
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
