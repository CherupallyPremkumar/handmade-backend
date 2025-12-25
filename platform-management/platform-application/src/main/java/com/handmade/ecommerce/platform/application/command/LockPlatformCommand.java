package com.handmade.ecommerce.platform.application.command;

/**
 * Command to lock/restrict the platform.
 * Application layer DTO - no business logic.
 */
public class LockPlatformCommand {
    private final String operatorId;
    private final String reason;

    public LockPlatformCommand(String operatorId, String reason) {
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
