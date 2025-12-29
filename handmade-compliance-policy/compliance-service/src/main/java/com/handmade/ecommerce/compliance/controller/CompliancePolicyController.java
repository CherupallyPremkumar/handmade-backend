package com.handmade.ecommerce.compliance.controller;

import com.handmade.ecommerce.compliance.domain.aggregate.CompliancePolicy;
import com.handmade.ecommerce.compliance.service.CompliancePolicyResolver;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST API for compliance policy resolution
 */
@RestController
@RequestMapping("/api/policies/compliance")
public class CompliancePolicyController {
    
    @Autowired
    private CompliancePolicyResolver policyResolver;
    
    /**
     * Resolve active policy
     * GET /api/policies/compliance/resolve?country=US&sellerType=INDIVIDUAL
     */
    @GetMapping("/resolve")
    public ResponseEntity<CompliancePolicy> resolvePolicy(
        @RequestParam String country,
        @RequestParam SellerType sellerType
    ) {
        CompliancePolicy policy = policyResolver.resolveActivePolicy(country, sellerType);
        return ResponseEntity.ok(policy);
    }
    
    /**
     * Get policy by version
     * GET /api/policies/compliance/2024.1-US-INDIVIDUAL
     */
    @GetMapping("/{version}")
    public ResponseEntity<CompliancePolicy> getPolicyByVersion(@PathVariable String version) {
        CompliancePolicy policy = policyResolver.getPolicyByVersion(version);
        return ResponseEntity.ok(policy);
    }
    
    /**
     * Get all active policies
     */
    @GetMapping("/active")
    public ResponseEntity<List<CompliancePolicy>> getAllActive() {
        return ResponseEntity.ok(policyResolver.getAllActivePolicies());
    }
    
    @ExceptionHandler(CompliancePolicyResolver.PolicyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePolicyNotFound(
        CompliancePolicyResolver.PolicyNotFoundException ex
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
