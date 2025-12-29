package com.handmade.ecommerce.payment.dto;

import java.io.Serializable;

/**
 * DTO for bank account details
 * Used for validation and verification requests
 */
public class BankAccountDetails implements Serializable {

    private String accountNumber;
    private String ifscCode; // For India (Razorpay)
    private String routingNumber; // For USA (Stripe ACH)
    private String swiftCode; // For International
    private String accountHolderName;
    private String bankName;
    private String countryCode; // ISO 2-letter code (IN, US, GB, etc.)
    private String accountType; // SAVINGS, CURRENT, CHECKING
    private String sellerId; // Reference to seller

    // Getters and Setters

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public String toString() {
        return "BankAccountDetails{" +
                "accountNumber='" + maskAccountNumber() + '\'' +
                ", ifscCode='" + ifscCode + '\'' +
                ", accountHolderName='" + accountHolderName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                '}';
    }

    /**
     * Masks account number for logging (shows last 4 digits only)
     */
    private String maskAccountNumber() {
        if (accountNumber == null || accountNumber.length() < 4) {
            return "****";
        }
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }
}
