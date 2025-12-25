package com.handmade.ecommerce.platform.application.command;

/**
 * Command to activate the platform.
 * Application layer DTO - no business logic.
 */
public class ActivatePlatformCommand {
    private final String operatorId;
    private final String reason;

    public ActivatePlatformCommand(String operatorId, String reason) {
        this.operatorId = operatorId;
        this.reason = reason;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public String getReason() {
        return reason;
    }
}
