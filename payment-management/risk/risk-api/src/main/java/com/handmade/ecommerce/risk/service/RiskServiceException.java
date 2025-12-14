package com.handmade.ecommerce.risk.service;

/**
 * Exception thrown when risk assessment fails.
 */
public class RiskServiceException extends RuntimeException {
    
    public RiskServiceException(String message) {
        super(message);
    }
    
    public RiskServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
