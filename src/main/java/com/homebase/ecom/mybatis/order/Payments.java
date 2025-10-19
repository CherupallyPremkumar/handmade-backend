package com.homebase.ecom.mybatis.order;

import java.util.List;

public class Payments {
    private String paymentId;
    private String amount;
    private List<PaymentLines> paymentLines;

    public String getPaymentId() {
        return paymentId;
    }

    public Payments setPaymentId(String paymentId) {
        this.paymentId = paymentId;
        return this;
    }



    public String getAmount() {
        return amount;
    }

    public Payments setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public List<PaymentLines> getPaymentLines() {
        return paymentLines;
    }

    public Payments setPaymentLines(List<PaymentLines> paymentLines) {
        this.paymentLines = paymentLines;
        return this;
    }
}
