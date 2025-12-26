package com.handmade.ecommerce.ledger.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Request object for creating a double-entry ledger transaction.
 * 
 * Double-entry bookkeeping requires:
 * - One debit entry
 * - One credit entry
 * - Debit amount = Credit amount
 */
public class CreateLedgerRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Debit account (money leaving)
     */
    private String debitAccount;

    /**
     * Credit account (money entering)
     */
    private String creditAccount;

    /**
     * Amount for the transaction
     */
    private BigDecimal amount;

    /**
     * Currency code (INR, USD, etc.)
     */
    private String currency;

    /**
     * Transaction status (PENDING, SETTLED, REVERSED)
     */
    private String status;

    /**
     * Transaction description
     */
    private String description;

    /**
     * Reference ID (PaymentOrder ID, Payout ID, etc.)
     */
    private String referenceId;

    /**
     * Reference type (PAYMENT_ORDER, PAYOUT, REFUND, etc.)
     */
    private String referenceType;

    // Constructors
    public CreateLedgerRequest() {
    }

    public CreateLedgerRequest(String debitAccount, String creditAccount, BigDecimal amount,
            String currency, String status, String description, String referenceId) {
        this.debitAccount = debitAccount;
        this.creditAccount = creditAccount;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.description = description;
        this.referenceId = referenceId;
        this.referenceType = "PAYMENT_ORDER"; // Default
    }

    // Getters and Setters
    public String getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(String debitAccount) {
        this.debitAccount = debitAccount;
    }

    public String getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(String creditAccount) {
        this.creditAccount = creditAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }
}
