package com.handmade.ecommerce.seller.domain.valueobject;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

/**
 * Bank account details for seller payouts
 * NOT STORED IN PRIMARY DATABASE - Managed via secure vault/provider
 */
@Embeddable
public class BankingInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String accountHolderName;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String routingNumber;
    private String swiftCode;
    private String accountType;

    public BankingInfo() {}

    public String getAccountHolderName() { return accountHolderName; }
    public void setAccountHolderName(String accountHolderName) { this.accountHolderName = accountHolderName; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getIfscCode() { return ifscCode; }
    public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }

    public String getRoutingNumber() { return routingNumber; }
    public void setRoutingNumber(String routingNumber) { this.routingNumber = routingNumber; }

    public String getSwiftCode() { return swiftCode; }
    public void setSwiftCode(String swiftCode) { this.swiftCode = swiftCode; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
}
