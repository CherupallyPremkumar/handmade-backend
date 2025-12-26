package com.handmade.ecommerce.payout.domain.valueobject;

/**
 * Payout frequency options
 */
public enum PayoutFrequency {
    DAILY("Daily payouts"),
    WEEKLY("Weekly payouts"),
    BIWEEKLY("Bi-weekly payouts"),
    MONTHLY("Monthly payouts"),
    ON_DEMAND("On-demand payouts for premium sellers");
    
    private final String description;
    
    PayoutFrequency(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
}
