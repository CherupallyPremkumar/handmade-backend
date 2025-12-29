package com.handmade.ecommerce.paymentexecutor;

/**
 * PaymentExecutorException
 * 
 * Exception thrown when payment executor operations fail.
 */
public class PaymentExecutorException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;
    
    private String pspType;
    private String errorCode;
    
    public PaymentExecutorException(String message) {
        super(message);
    }
    
    public PaymentExecutorException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public PaymentExecutorException(String message, String pspType, String errorCode) {
        super(message);
        this.pspType = pspType;
        this.errorCode = errorCode;
    }
    
    public PaymentExecutorException(String message, Throwable cause, String pspType, String errorCode) {
        super(message, cause);
        this.pspType = pspType;
        this.errorCode = errorCode;
    }
    
    public String getPspType() {
        return pspType;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
}
