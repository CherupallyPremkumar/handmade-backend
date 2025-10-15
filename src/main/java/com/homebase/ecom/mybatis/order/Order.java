package com.homebase.ecom.mybatis.order;

import java.util.List;

public class Order {
    private String id;
    private String status;
    private String createdTime;
    private String orderNo;
    private String transactionType;
    private String customerId;
    private String customerName;
    private String customerEmail;
    private String comments;
    private String employeeId;
    private String employeeName;
    private String txn;
    private String paymentId;
    private String paymentMode;
    private String taxAmount;
    private String totalAmount;
    private String transactionAmount;

    private String paidAmount;

    private String remainingAmount;
    private String shiftId;
    private String registerId;
    private String locationId;
    private String locationName;


    private String subsidiaryId;
    private String deliveredCount;
    private String tenantId;
    private String layWayPay;
    private List<OrderItems> orderLines;
    private Payments payments;

    public String getId() {
        return id;
    }

    public Order setId(String id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Order setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public Order setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public Order setTransactionType(String transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Order setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getComments() {
        return comments;
    }

    public Order setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Order setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public String getTxn() {
        return txn;
    }

    public Order setTxn(String txn) {
        this.txn = txn;
        return this;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public Order setPaymentId(String paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public Order setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public Order setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public Order setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public Order setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public String getShiftId() {
        return shiftId;
    }

    public Order setShiftId(String shiftId) {
        this.shiftId = shiftId;
        return this;
    }


    public String getLocationId() {
        return locationId;
    }

    public Order setLocationId(String locationId) {
        this.locationId = locationId;
        return this;
    }

    public String getSubsidiaryId() {
        return subsidiaryId;
    }

    public Order setSubsidiaryId(String subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
        return this;
    }

    public String getDeliveredCount() {
        return deliveredCount;
    }

    public Order setDeliveredCount(String deliveredCount) {
        this.deliveredCount = deliveredCount;
        return this;
    }

    public String getTenantId() {
        return tenantId;
    }

    public Order setTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public String getLayWayPay() {
        return layWayPay;
    }

    public Order setLayWayPay(String layWayPay) {
        this.layWayPay = layWayPay;
        return this;
    }

    public List<OrderItems> getOrderLines() {
        return orderLines;
    }

    public Order setOrderLines(List<OrderItems> orderLines) {
        this.orderLines = orderLines;
        return this;
    }



    public String getCustomerName() {
        return customerName;
    }

    public Order setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Order setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
        return this;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Order setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public Order setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
        return this;
    }

    public String getRemainingAmount() {
        return remainingAmount;
    }

    public Order setRemainingAmount(String remainingAmount) {
        this.remainingAmount = remainingAmount;
        return this;
    }

    public String getLocationName() {
        return locationName;
    }

    public Order setLocationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public String getRegisterId() {
        return registerId;
    }

    public Order setRegisterId(String registerId) {
        this.registerId = registerId;
        return this;
    }

    public Payments getPayments() {
        return payments;
    }

    public Order setPayments(Payments payments) {
        this.payments = payments;
        return this;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}
