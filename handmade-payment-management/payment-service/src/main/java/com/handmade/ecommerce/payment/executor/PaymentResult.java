package com.handmade.ecommerce.payment.executor;

/**
 * Payment Result
 * Returned by PSP after payment execution
 */
public class PaymentResult {

    private boolean success;
    private String pspToken;
    private String errorMessage;

    public PaymentResult(boolean success, String pspToken, String errorMessage) {
        this.success = success;
        this.pspToken = pspToken;
        this.errorMessage = errorMessage;
    }

    public static PaymentResult success(String pspToken) {
        return new PaymentResult(true, pspToken, null);
    }

    public static PaymentResult failure(String errorMessage) {
        return new PaymentResult(false, null, errorMessage);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getPspToken() {
        return pspToken;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
