package com.handmade.ecommerce.onboarding.domain.valueobject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Bank Account Information (PII)
 * 
 * SECURITY: Should be encrypted at rest in production
 * Consider using @Convert with encryption converter
 */
@Embeddable
public class BankAccountInfo {

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "account_type")
    private String accountType; // "CHECKING", "SAVINGS"

    @Column(name = "routing_number")
    private String routingNumber; // Encrypted in production

    @Column(name = "account_number_last4")
    private String accountNumberLast4; // Only last 4 digits

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_country")
    private String country; // ISO 3166-1 alpha-2

    @Column(name = "bank_currency")
    private String currency; // ISO 4217 (USD, EUR, etc.)

    // Constructors
    public BankAccountInfo() {
    }

    public BankAccountInfo(String accountHolderName, String accountType,
            String routingNumber, String accountNumberLast4,
            String bankName, String country, String currency) {
        this.accountHolderName = accountHolderName;
        this.accountType = accountType;
        this.routingNumber = routingNumber;
        this.accountNumberLast4 = accountNumberLast4;
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

    public String getAccountNumberLast4() {
        return accountNumberLast4;
    }

    public void setAccountNumberLast4(String accountNumberLast4) {
        this.accountNumberLast4 = accountNumberLast4;
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
