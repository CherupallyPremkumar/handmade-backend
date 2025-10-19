package com.homebase.ecom.mybatis.orderlist;

public class OrderList {
    private String id;
    private String status;
    private String orderNo;
    private String createdTime;
    private String transactionType;
    private String customerId;
    private String customerName;
    private String comments;
    private String employeeId;
    private String txn;
    private String paymentId;
    private String paymentMode;
    private String taxAmount;
    private String totalAmount;
    private String transactionAmount;
    private String shiftId;
    private String counterId;
    private String locationId;
    private String subsidiaryId;
    private Integer deliveredCount;
    private String tenantId;
    private String layWayPay;
    private String SavedDateTime;

    public String getId() {
        return id;
    }

    public OrderList setId(String id) {
        this.id = id;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public OrderList setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public OrderList setOrderNo(String orderNo) {
        this.orderNo = orderNo;
        return this;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public OrderList setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public OrderList setTransactionType(String transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public String getCustomerId() {
        return customerId;
    }

    public OrderList setCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }

    public OrderList setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public String getComments() {
        return comments;
    }

    public OrderList setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public OrderList setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
        return this;
    }

    public String getTxn() {
        return txn;
    }

    public OrderList setTxn(String txn) {
        this.txn = txn;
        return this;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public OrderList setPaymentId(String paymentId) {
        this.paymentId = paymentId;
        return this;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public OrderList setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
        return this;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public OrderList setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
        return this;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public OrderList setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public OrderList setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public String getShiftId() {
        return shiftId;
    }

    public OrderList setShiftId(String shiftId) {
        this.shiftId = shiftId;
        return this;
    }

    public String getCounterId() {
        return counterId;
    }

    public OrderList setCounterId(String counterId) {
        this.counterId = counterId;
        return this;
    }

    public String getLocationId() {
        return locationId;
    }

    public OrderList setLocationId(String locationId) {
        this.locationId = locationId;
        return this;
    }

    public String getSubsidiaryId() {
        return subsidiaryId;
    }

    public OrderList setSubsidiaryId(String subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
        return this;
    }

    public Integer getDeliveredCount() {
        return deliveredCount;
    }

    public OrderList setDeliveredCount(Integer deliveredCount) {
        this.deliveredCount = deliveredCount;
        return this;
    }

    public String getTenantId() {
        return tenantId;
    }

    public OrderList setTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public String getLayWayPay() {
        return layWayPay;
    }

    public OrderList setLayWayPay(String layWayPay) {
        this.layWayPay = layWayPay;
        return this;
    }

    public String getSavedDateTime() {
        return SavedDateTime;
    }

    public OrderList setSavedDateTime(String savedDateTime) {
        SavedDateTime = savedDateTime;
        return this;
    }
}
