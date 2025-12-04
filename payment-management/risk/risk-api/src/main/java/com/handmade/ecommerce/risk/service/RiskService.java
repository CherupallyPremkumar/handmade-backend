package com.handmade.ecommerce.risk.service;

import com.handmade.ecommerce.risk.model.RiskAssessment;

import java.math.BigDecimal;

/**
 * Service for assessing payment transaction risk.
 * 
 * This service evaluates the risk level of payment transactions
 * using external fraud detection services (Sift, Riskified, etc.)
 * and internal business rules.
 * 
 * Risk assessment helps prevent:
 * - Fraudulent transactions
 * - Money laundering (AML)
 * - Terrorism financing (CFT)
 * - Chargebacks
 * - Account takeover
 */
public interface RiskService {
    
    /**
     * Assesses risk for a payment transaction.
     * 
     * @param buyerId Buyer's user ID
     * @param amount Payment amount
     * @param currency Currency code (INR, USD, EUR, etc.)
     * @param ipAddress Buyer's IP address
     * @param deviceId Buyer's device fingerprint
     * @return Risk assessment result with level, score, and reason
     * @throws RiskServiceException if risk assessment fails
     */
    RiskAssessment assessRisk(
        String buyerId,
        BigDecimal amount,
        String currency,
        String ipAddress,
        String deviceId
    );
    
    /**
     * Checks if buyer is on sanctions list or blacklist.
     * 
     * @param buyerId Buyer's user ID
     * @return true if buyer is blacklisted
     */
    boolean isBlacklisted(String buyerId);
    
    /**
     * Records successful transaction for risk scoring.
     * Used to build buyer's trust score over time.
     * 
     * @param buyerId Buyer's user ID
     * @param amount Transaction amount
     */
    void recordSuccessfulTransaction(String buyerId, BigDecimal amount);
}
