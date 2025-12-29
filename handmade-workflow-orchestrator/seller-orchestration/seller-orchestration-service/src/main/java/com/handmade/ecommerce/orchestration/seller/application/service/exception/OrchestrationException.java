package com.handmade.ecommerce.orchestration.seller.application.service.exception;

/**
 * Thrown when an orchestration operation fails due to unexpected errors.
 */
public class OrchestrationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public OrchestrationException(String message) {
        super(message);
    }

    public OrchestrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
