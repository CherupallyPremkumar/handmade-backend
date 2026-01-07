package com.handmade.ecommerce.commission.domain.valueobject;

/**
 * Types of commission and fees that can be defined in a policy
 */
public enum CommissionFeeType {
    REFERRAL_FEE,        // Standard commission on sale
    PROCESSING_FEE,      // Payment processing fee
    SUBSCRIPTION_FEE,    // Recurring seller subscription
    LISTING_FEE,         // Fee per product listed
    LOGISTICS_FEE        // Platform-managed shipping fee
}
