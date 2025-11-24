package com.handmade.ecommerce.seller.dto;

import java.util.List;

public class SellerPaymentInfoRequest {
    private String preferredMethod;
    private String currency;
    private List<PaymentMethodRequest> paymentMethods;

    public String getPreferredMethod() {
        return preferredMethod;
    }

    public void setPreferredMethod(String preferredMethod) {
        this.preferredMethod = preferredMethod;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<PaymentMethodRequest> getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(List<PaymentMethodRequest> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
