package com.homebase.ecom.mybatis.cart;



import java.util.List;

public class CartDto {


    private String id;
    private String status;
    private String customerId;
    private String txn;
    private String taxAmount;
    private String totalAmount;
    private String transactionAmount;
    private String locationId;
    private String tenantId;
    private List<CartItemsDto> orderLines;

    public String getId() {
        return id;
    }

    public CartDto setId(String id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public CartDto setStatus(String status) {
        this.status = status;
        return this;
    }

    public String customerId() {
        return customerId;
    }

    public CartDto setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String txn() {
        return txn;
    }

    public CartDto setTxn(String txn) {
        this.txn = txn;
        return this;
    }

    public String taxAmount() {
        return taxAmount;
    }

    public CartDto setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    public String totalAmount() {
        return totalAmount;
    }

    public CartDto setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String transactionAmount() {
        return transactionAmount;
    }

    public CartDto setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public String locationId() {
        return locationId;
    }

    public CartDto setLocationId(String locationId) {
        this.locationId = locationId;
        return this;
    }

    public String tenantId() {
        return tenantId;
    }

    public CartDto setTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public List<CartItemsDto> orderLines() {
        return orderLines;
    }

    public CartDto setOrderLines(List<CartItemsDto> orderLines) {
        this.orderLines = orderLines;
        return this;
    }
}
