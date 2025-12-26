package com.handmade.ecommerce.seller.domain.valueobject;

import com.handmade.ecommerce.seller.domain.enums.PayoutSchedule;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Banking and payout information
 * IMPORTANT: Sensitive data should be encrypted at rest
 */
@Embeddable
public class BankingInfo implements Serializable {
    
    private String accountHolderName;
    private String bankName;
    
    // TODO: These should be encrypted
    private String encryptedAccountNumber;
    private String routingNumber;
    private String swiftCode;  // For international
    
    private String currency;  // ISO 4217 code (e.g., "USD")
    
    @Enumerated(EnumType.STRING)
    private PayoutSchedule schedule;
    
    private BigDecimal minimumPayout;  // Don't transfer if below this

    public BankingInfo() {
        this.minimumPayout = new BigDecimal("10.00");  // Default $10
        this.schedule = PayoutSchedule.WEEKLY;
    }

    // Getters and Setters
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

    public String getEncryptedAccountNumber() {
        return encryptedAccountNumber;
    }

    public void setEncryptedAccountNumber(String encryptedAccountNumber) {
        this.encryptedAccountNumber = encryptedAccountNumber;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public PayoutSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(PayoutSchedule schedule) {
        this.schedule = schedule;
    }

    public BigDecimal getMinimumPayout() {
        return minimumPayout;
    }

    public void setMinimumPayout(BigDecimal minimumPayout) {
        this.minimumPayout = minimumPayout;
    }
}
