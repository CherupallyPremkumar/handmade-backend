package com.handmade.ecommerce.risk.client;

import com.handmade.ecommerce.risk.client.model.SiftRequest;
import com.handmade.ecommerce.risk.client.model.SiftResponse;
import com.handmade.ecommerce.risk.configuration.RiskServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * HTTP client for Sift fraud detection API.
 * 
 * Sift API Documentation: https://sift.com/developers/docs/curl/score-api
 */
@Component
public class SiftClient {
    
    private static final Logger log = LoggerFactory.getLogger(SiftClient.class);
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private RiskServiceConfig config;
    
    /**
     * Calls Sift API to check transaction risk.
     * 
     * @param request Sift request with transaction details
     * @return Sift response with risk score
     */
    public SiftResponse checkRisk(SiftRequest request) {
        if (!config.isEnabled()) {
            log.warn("Risk service is disabled. Returning low risk.");
            return SiftResponse.lowRisk();
        }
        
        try {
            String url = config.getUrl() + "/v205/score/" + request.getUserId();
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + config.getApiKey());
            
            HttpEntity<SiftRequest> entity = new HttpEntity<>(request, headers);
            
            log.debug("Calling Sift API: {}", url);
            
            ResponseEntity<SiftResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                SiftResponse.class
            );
            
            return response.getBody();
            
        } catch (Exception e) {
            log.error("Sift API call failed", e);
            throw new RuntimeException("Risk service unavailable", e);
        }
    }
}
