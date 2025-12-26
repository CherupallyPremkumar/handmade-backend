package com.handmade.ecommerce.risk.provider.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Generic risk check response (provider-agnostic).
 */
public class RiskCheckResponse {
    
    private Double score; // 0.0 - 1.0
    private String riskLevel; // LOW, MEDIUM, HIGH
    private String reason;
    private List<String> riskFactors = new ArrayList<>();
    
    public static RiskCheckResponse lowRisk() {
        RiskCheckResponse response = new RiskCheckResponse();
        response.setScore(0.1);
        response.setRiskLevel("LOW");
        response.setReason("Normal transaction");
        return response;
    }
    
    // Getters and setters
    public Double getScore() {
        return score;
    }
    
    public void setScore(Double score) {
        this.score = score;
    }
    
    public String getRiskLevel() {
        return riskLevel;
    }
    
    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public List<String> getRiskFactors() {
        return riskFactors;
    }
    
    public void setRiskFactors(List<String> riskFactors) {
        this.riskFactors = riskFactors;
    }
}
