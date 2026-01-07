package com.handmade.ecommerce.payout.controller;

import com.handmade.ecommerce.payout.domain.aggregate.PayoutPolicy;
import com.handmade.ecommerce.payout.service.PayoutPolicyResolver;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API for payout policy resolution
 */
@RestController
@RequestMapping("/api/policies/payout")
public class PayoutPolicyController {
    
    @Autowired
    private PayoutPolicyResolver policyResolver;
    
    /**
     * Resolve active policy
     * GET /api/policies/payout/resolve?country=US&sellerType=INDIVIDUAL
     */
    @GetMapping("/resolve")
    public ResponseEntity<PayoutPolicy> resolvePolicy(
        @RequestParam String country,
        @RequestParam SellerType sellerType
    ) {
        PayoutPolicy policy = policyResolver.resolveActivePolicy(country, sellerType);
        return ResponseEntity.ok(policy);
    }
    
    /**
     * Get policy by version
     * GET /api/policies/payout/2024.1-US-INDIVIDUAL
     */
    @GetMapping("/{version}")
    public ResponseEntity<PayoutPolicy> getPolicyByVersion(@PathVariable String version) {
        PayoutPolicy policy = policyResolver.getPolicyByVersion(version);
        return ResponseEntity.ok(policy);
    }
    
    /**
     * Check payout availability
     * GET /api/policies/payout/available?country=US&sellerType=INDIVIDUAL
     */
    @GetMapping("/available")
    public ResponseEntity<Boolean> checkAvailability(
        @RequestParam String country,
        @RequestParam SellerType sellerType
    ) {
        boolean available = policyResolver.canPayoutInCountry(country, sellerType);
        return ResponseEntity.ok(available);
    }
    
    /**
     * Get all active policies
     */
    @GetMapping("/active")
    public ResponseEntity<List<PayoutPolicy>> getAllActive() {
        return ResponseEntity.ok(policyResolver.getAllActivePolicies());
    }
    
    @ExceptionHandler(PayoutPolicyResolver.PolicyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePolicyNotFound(
        PayoutPolicyResolver.PolicyNotFoundException ex
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
