package com.handmade.ecommerce.platform.api.exception;

public class PlatformAccessDeniedException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PlatformAccessDeniedException(String message) {
        super(message);
    }

    public PlatformAccessDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
