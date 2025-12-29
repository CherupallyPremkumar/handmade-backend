package com.handmade.ecommerce.risk.model;

/**
 * Risk level classification for payment transactions.
 * 
 * LOW: Normal transaction, proceed with payment
 * MEDIUM: Suspicious activity, may require manual review
 * HIGH: High fraud risk, reject payment
 */
public enum RiskLevel {
    
    /**
     * Low risk - proceed with payment.
     * Score: 0.0 - 0.4
     */
    LOW,
    
    /**
     * Medium risk - may require manual review.
     * Score: 0.4 - 0.7
     */
    MEDIUM,
    
    /**
     * High risk - reject payment.
     * Score: 0.7 - 1.0
     */
    HIGH
}
