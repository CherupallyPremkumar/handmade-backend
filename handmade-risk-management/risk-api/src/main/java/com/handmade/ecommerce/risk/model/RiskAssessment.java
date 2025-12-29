package com.handmade.ecommerce.risk.model;

/**
 * Risk assessment result for a payment transaction.
 * 
 * Contains:
 * - Risk level (LOW, MEDIUM, HIGH)
 * - Risk score (0.0 - 1.0)
 * - Reason for risk classification
 */
public class RiskAssessment {
    
    private RiskLevel riskLevel;
    private Double score;
    private String reason;
    private String riskFactors;
    
    public RiskAssessment() {
    }
    
    public RiskAssessment(RiskLevel riskLevel, Double score, String reason) {
        this.riskLevel = riskLevel;
        this.score = score;
        this.reason = reason;
    }
    
    /**
     * Creates a low-risk assessment.
     */
    public static RiskAssessment lowRisk() {
        return new RiskAssessment(RiskLevel.LOW, 0.1, "Normal transaction");
    }
    
    /**
     * Creates a high-risk assessment for service unavailability.
     */
    public static RiskAssessment serviceUnavailable() {
        return new RiskAssessment(
            RiskLevel.HIGH,
            1.0,
            "Risk service unavailable - failing safe"
        );
    }
    
    // Getters and setters
    public RiskLevel getRiskLevel() {
        return riskLevel;
    }
    
    public void setRiskLevel(RiskLevel riskLevel) {
        this.riskLevel = riskLevel;
    }
    
    public Double getScore() {
        return score;
    }
    
    public void setScore(Double score) {
        this.score = score;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getRiskFactors() {
        return riskFactors;
    }
    
    public void setRiskFactors(String riskFactors) {
        this.riskFactors = riskFactors;
    }
}
