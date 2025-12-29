package com.handmade.ecommerce.risk.service.impl;

import com.handmade.ecommerce.risk.client.SiftClient;
import com.handmade.ecommerce.risk.client.model.SiftRequest;
import com.handmade.ecommerce.risk.client.model.SiftResponse;
import com.handmade.ecommerce.risk.model.RiskAssessment;
import com.handmade.ecommerce.risk.model.RiskLevel;
import com.handmade.ecommerce.risk.service.RiskService;
import com.handmade.ecommerce.risk.service.RiskServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Risk service implementation that calls external Sift API.
 * 
 * Sift is a fraud detection service that uses machine learning
 * to identify fraudulent transactions.
 * 
 * See: https://sift.com/developers/docs/curl/score-api
 */
@Service
public class RiskServiceImpl implements RiskService {

    private static final Logger log = LoggerFactory.getLogger(RiskServiceImpl.class);
    
    @Autowired
    private SiftClient siftClient;

    @Override
    public RiskAssessment assessRisk(
        String buyerId,
        BigDecimal amount,
        String currency,
        String ipAddress,
        String deviceId
    ) {
        try {
            log.info("Assessing risk for buyer: {}, amount: {} {}", buyerId, amount, currency);
            
            // Build request for Sift API
            SiftRequest request = buildSiftRequest(buyerId, amount, currency, ipAddress, deviceId);
            
            // Call external Sift API
            SiftResponse response = siftClient.checkRisk(request);
            
            // Convert Sift response to our domain model
            RiskAssessment assessment = convertToRiskAssessment(response);
            
            log.info("Risk assessment complete. Level: {}, Score: {}", 
                assessment.getRiskLevel(), assessment.getScore());
            
            return assessment;
            
        } catch (Exception e) {
            log.error("Risk assessment failed for buyer: {}", buyerId, e);
            
            // Fail-safe: Return high risk if service is unavailable
            return RiskAssessment.serviceUnavailable();
        }
    }

    @Override
    public boolean isBlacklisted(String buyerId) {
        // TODO: Implement blacklist check
        // Could check against:
        // - Internal blacklist database
        // - OFAC sanctions list
        // - EU sanctions list
        return false;
    }

    @Override
    public void recordSuccessfulTransaction(String buyerId, BigDecimal amount) {
        // TODO: Record successful transaction for trust scoring
        log.info("Recording successful transaction for buyer: {}, amount: {}", buyerId, amount);
    }
    
    private SiftRequest buildSiftRequest(
        String buyerId,
        BigDecimal amount,
        String currency,
        String ipAddress,
        String deviceId
    ) {
        SiftRequest request = new SiftRequest();
        request.setUserId(buyerId);
        request.setAmount(amount.multiply(new BigDecimal("1000000")).longValue()); // Sift uses micros
        request.setCurrencyCode(currency);
        request.setIp(ipAddress);
        request.setSessionId(deviceId);
        return request;
    }
    
    private RiskAssessment convertToRiskAssessment(SiftResponse response) {
        double score = response.getScore();
        
        RiskLevel level;
        String reason;
        
        if (score >= 0.7) {
            level = RiskLevel.HIGH;
            reason = "High fraud risk detected";
        } else if (score >= 0.4) {
            level = RiskLevel.MEDIUM;
            reason = "Moderate fraud risk detected";
        } else {
            level = RiskLevel.LOW;
            reason = "Low fraud risk";
        }
        
        RiskAssessment assessment = new RiskAssessment(level, score, reason);
        assessment.setRiskFactors(String.join(", ", response.getRiskFactors()));
        
        return assessment;
    }
}
