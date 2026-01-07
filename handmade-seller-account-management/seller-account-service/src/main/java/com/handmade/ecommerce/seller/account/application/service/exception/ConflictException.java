package com.handmade.ecommerce.seller.account.application.service.exception;

/**
 * Thrown when a request conflicts with the current state of the system
 * (e.g., duplicate onboarding case).
 */
public class ConflictException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ConflictException(String message) {
        super(message);
    }
}
