package com.handmade.ecommerce.platform.application.exception;

/**
 * Exception thrown when an operator lacks permission for platform operations.
 */
public class PlatformAccessDeniedException extends RuntimeException {
    private final String operatorId;
    private final String operation;

    public PlatformAccessDeniedException(String operatorId, String operation) {
        super(String.format("Operator %s is not authorized to perform operation: %s", 
                          operatorId, operation));
        this.operatorId = operatorId;
        this.operation = operation;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public String getOperation() {
        return operation;
    }
}
