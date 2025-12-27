package com.handmade.ecommerce.policy.controller;

import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.domain.entity.OnboardingPolicyRule;
import com.handmade.ecommerce.policy.exception.PolicyNotFoundException;
import com.handmade.ecommerce.policy.service.OnboardingPolicyService;
import com.handmade.ecommerce.policy.service.OnboardingPolicyResolver;
import com.handmade.ecommerce.seller.domain.enums.SellerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API for onboarding policy resolution
 * Called by seller service to get applicable policies
 */
@RestController
@RequestMapping("/api/policies/onboarding")
public class OnboardingPolicyController {
    
    @Autowired
    private OnboardingPolicyResolver policyResolver;
    
    /**
     * Resolve active policy for country and seller type
     * 
     * GET /api/policies/onboarding/resolve?country=US&sellerType=INDIVIDUAL
     */
    @GetMapping("/resolve")
    public ResponseEntity<OnboardingPolicy> resolvePolicy(
        @RequestParam String country,
        @RequestParam SellerType sellerType
    ) {
        OnboardingPolicy policy = policyResolver.resolveActivePolicy(country, sellerType);
        return ResponseEntity.ok(policy);
    }
    
    /**
     * Get policy by version (for locked sellers)
     * 
     * GET /api/policies/onboarding/2024.1-US-INDIVIDUAL
     */
    @GetMapping("/{version}")
    public ResponseEntity<OnboardingPolicy> getPolicyByVersion(@PathVariable String version) {
        OnboardingPolicy policy = policyResolver.getPolicyByVersion(version);
        return ResponseEntity.ok(policy);
    }
    
    /**
     * Check if onboarding is available for country
     * 
     * GET /api/policies/onboarding/available?country=US&sellerType=INDIVIDUAL
     */
    @GetMapping("/available")
    public ResponseEntity<Boolean> checkAvailability(
        @RequestParam String country,
        @RequestParam SellerType sellerType
    ) {
        boolean available = policyResolver.canOnboardInCountry(country, sellerType);
        return ResponseEntity.ok(available);
    }
    
    /**
     * Get all active policies (admin)
     * 
     * GET /api/policies/onboarding/active
     */
    @GetMapping("/active")
    public ResponseEntity<List<OnboardingPolicy>> getAllActive() {
        List<OnboardingPolicy> policies = policyResolver.getAllActivePolicies();
        return ResponseEntity.ok(policies);
    }
    
    /**
     * Get all draft policies (admin)
     * 
     * GET /api/policies/onboarding/drafts
     */
    @GetMapping("/drafts")
    public ResponseEntity<List<OnboardingPolicy>> getAllDrafts() {
        List<OnboardingPolicy> policies = policyResolver.getAllDraftPolicies();
        return ResponseEntity.ok(policies);
    }
    
    /**
     * Exception handler for policy not found
     */
    @ExceptionHandler(PolicyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePolicyNotFound(
        PolicyNotFoundException ex
    ) {
        ErrorResponse error = new ErrorResponse("POLICY_NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(404).body(error);
    }
    
    /**
     * Error response DTO
     */
    public static class ErrorResponse {
        public String code;
        public String message;
        
        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}
