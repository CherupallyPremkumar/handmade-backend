package com.handmade.ecommerce.risk.client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Response model from Sift API.
 * 
 * See: https://sift.com/developers/docs/curl/score-api/score-response
 */
public class SiftResponse {
    
    private Double score;
    private List<String> riskFactors = new ArrayList<>();
    private String status;
    
    /**
     * Creates a low-risk response (for when service is disabled).
     */
    public static SiftResponse lowRisk() {
        SiftResponse response = new SiftResponse();
        response.setScore(0.1);
        response.setStatus("success");
        response.getRiskFactors().add("service_disabled");
        return response;
    }
    
    // Getters and setters
    public Double getScore() {
        return score;
    }
    
    public void setScore(Double score) {
        this.score = score;
    }
    
    public List<String> getRiskFactors() {
        return riskFactors;
    }
    
    public void setRiskFactors(List<String> riskFactors) {
        this.riskFactors = riskFactors;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
}
