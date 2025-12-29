package com.handmade.ecommerce.onboarding.dto;

/**
 * Request to submit bank account information
 */
public class BankAccountRequest {

    private String accountHolderName;
    private String accountType; // "CHECKING", "SAVINGS"
    private String routingNumber;
    private String accountNumber;
    private String bankName;
    private String country; // ISO 3166-1 alpha-2
    private String currency; // ISO 4217 (USD, EUR, etc.)

    // Constructors
    public BankAccountRequest() {
    }

    public BankAccountRequest(String accountHolderName, String accountType,
            String routingNumber, String accountNumber,
            String bankName, String country, String currency) {
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.routingNumber = routingNumber;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.country = country;
        this.currency = currency;
    }

    // Getters and Setters

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
