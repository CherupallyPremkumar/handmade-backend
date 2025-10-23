package com.homebase.ecom.errorcodes;

public enum ErrorCodes {
    PRICE_NOT_FOUND(6000),
    PRICE_CURRENCY_NOT_FOUND(6001),
    PAYMENT_CURRENCY_IS_NULL(6003),
    PRICELINE_NOT_FOUND(6002),

    PRICE_LINE_INTERNAL_ERROR(6003);


    final int subError;

    ErrorCodes(int subError) {
        this.subError = subError;
    }

    public int getSubError() {
        return this.subError;
    }
}
