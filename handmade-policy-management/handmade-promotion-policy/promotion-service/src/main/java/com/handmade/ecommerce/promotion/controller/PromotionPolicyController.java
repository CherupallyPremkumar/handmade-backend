package com.handmade.ecommerce.promotion.controller;

import com.handmade.ecommerce.promotion.domain.aggregate.PromotionPolicy;
import com.handmade.ecommerce.promotion.service.PromotionPolicyResolver;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API for promotion policy resolution
 */
@RestController
@RequestMapping("/api/policies/promotion")
public class PromotionPolicyController {
    
    @Autowired
    private PromotionPolicyResolver policyResolver;
    
    /**
     * Resolve active policy
     * GET /api/policies/promotion/resolve?country=US&sellerType=INDIVIDUAL
     */
    @GetMapping("/resolve")
    public ResponseEntity<PromotionPolicy> resolvePolicy(
        @RequestParam String country,
        @RequestParam SellerType sellerType
    ) {
        PromotionPolicy policy = policyResolver.resolveActivePolicy(country, sellerType);
        return ResponseEntity.ok(policy);
    }
    
    /**
     * Get policy by version
     * GET /api/policies/promotion/2024.1-US-INDIVIDUAL
     */
    @GetMapping("/{version}")
    public ResponseEntity<PromotionPolicy> getPolicyByVersion(@PathVariable String version) {
        PromotionPolicy policy = policyResolver.getPolicyByVersion(version);
        return ResponseEntity.ok(policy);
    }
    
    /**
     * Get all currently active promotions
     * GET /api/policies/promotion/current
     */
    @GetMapping("/current")
    public ResponseEntity<List<PromotionPolicy>> getCurrentPromotions() {
        return ResponseEntity.ok(policyResolver.getAllActivePromotionsToday());
    }
    
    /**
     * Get all active policies
     */
    @GetMapping("/active")
    public ResponseEntity<List<PromotionPolicy>> getAllActive() {
        return ResponseEntity.ok(policyResolver.getAllActivePolicies());
    }
    
    @ExceptionHandler(PromotionPolicyResolver.PolicyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePolicyNotFound(
        PromotionPolicyResolver.PolicyNotFoundException ex
    ) {
        return ResponseEntity.status(404).body(new ErrorResponse("POLICY_NOT_FOUND", ex.getMessage()));
    }
    
    public static class ErrorResponse {
        public String code;
        public String message;
        
        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
