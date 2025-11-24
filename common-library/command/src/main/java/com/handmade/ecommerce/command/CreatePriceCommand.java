package com.handmade.ecommerce.command;

public class CreatePriceCommand {
    private String id;
    private String variantId;
    private Double amount;
    private String currency;
    private Double discountAmount;   // Optional
    private Double finalPayable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getFinalPayable() {
        return finalPayable;
    }

    public void setFinalPayable(Double finalPayable) {
        this.finalPayable = finalPayable;
    }
}
