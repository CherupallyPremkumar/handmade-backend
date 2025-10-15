package com.homebase.ecom.mybatis.order;

import java.math.BigDecimal;

public class PaymentLines {

    private String id;
    private String tenantId;
    private String referenceNumber;

    private String method;
    private BigDecimal amount;

    public String getTenantId() {
        return tenantId;
    }

    public PaymentLines setTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public PaymentLines setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public PaymentLines setMethod(String method) {
        this.method = method;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public String getId() {
        return id;
    }

    public PaymentLines setId(String id) {
        this.id = id;
        return this;
    }
}
