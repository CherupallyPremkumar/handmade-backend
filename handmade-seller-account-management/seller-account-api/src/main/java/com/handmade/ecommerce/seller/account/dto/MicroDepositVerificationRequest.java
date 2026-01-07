package com.handmade.ecommerce.seller.account.dto;

/**
 * Request to verify micro-deposit amounts
 */
public class MicroDepositVerificationRequest {

    private int amount1; // In cents
    private int amount2; // In cents

    // Constructors
    public MicroDepositVerificationRequest() {
    }

    public MicroDepositVerificationRequest(int amount1, int amount2) {
        this.amount1 = amount1;
        this.amount2 = amount2;
    }

    // Getters and Setters

    public int getAmount1() {
        return amount1;
    }

    public void setAmount1(int amount1) {
        this.amount1 = amount1;
    }

    public int getAmount2() {
        return amount2;
    }

    public void setAmount2(int amount2) {
        this.amount2 = amount2;
    }
}
