package com.handmade.ecommerce.policy.configuration.controller;

import com.handmade.ecommerce.policy.domain.aggregate.PricingPolicy;
import com.handmade.ecommerce.policy.api.PricingPolicyManager;
import com.handmade.ecommerce.policy.domain.valueobject.PricingDecision;
import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.BodyTypeSelector;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.ChenileParamType;
import org.chenile.http.handler.ControllerSupport;
import org.chenile.stm.StateEntity;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Enterprise REST API for pricing policy management.
 */
@RestController
@ChenileController(value = "pricingPolicyService", serviceName = "_pricingPolicyStateEntityService_",
        healthCheckerName = "pricingPolicyHealthChecker")
public class PricingPolicyController extends ControllerSupport {
    
    @Autowired
    private PricingPolicyManager policyManager;

    // ========== GENERIC CRUD & WORKFLOW (Chenile Pattern) ==========

    @GetMapping("/pricing-policy/{id}")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<PricingPolicy>>> retrieve(
            HttpServletRequest httpServletRequest,
            @PathVariable String id) {
        return process(httpServletRequest, id);
    }

    @PostMapping("/pricing-policy")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<PricingPolicy>>> create(
            HttpServletRequest httpServletRequest,
            @ChenileParamType(StateEntity.class)
            @RequestBody PricingPolicy entity) {
        return process(httpServletRequest, entity);
    }

    @PatchMapping("/pricing-policy/{id}/{eventID}")
    @BodyTypeSelector("pricingPolicyBodyTypeSelector")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<PricingPolicy>>> processById(
            HttpServletRequest httpServletRequest,
            @PathVariable String id,
            @PathVariable String eventID,
            @ChenileParamType(Object.class) 
            @RequestBody String eventPayload) {
        return process(httpServletRequest, id, eventID, eventPayload);
    }

    // ========== BUSINESS SPECIFIC ENDPOINTS ==========
    
    /**
     * Resolve active policy for country and category
     */
    @GetMapping("/api/policies/pricing/resolve")
    public ResponseEntity<PricingPolicy> resolvePolicy(
        @RequestParam String country,
        @RequestParam(required = false) String category
    ) {
        PricingPolicy policy = policyManager.resolveActivePolicy(country, category, LocalDate.now());
        return ResponseEntity.ok(policy);
    }
    
    /**
     * Validate price against active policy
     */
    @GetMapping("/api/policies/pricing/validate")
    public ResponseEntity<PricingDecision> validatePrice(
        @RequestParam String country,
        @RequestParam(required = false) String category,
        @RequestParam Long priceCents,
        @RequestParam String productId,
        @RequestParam String sellerId
    ) {
        PricingDecision decision = policyManager.validatePrice(country, category, priceCents, productId, sellerId);
        return ResponseEntity.ok(decision);
    }

    /**
     * Get policy by version
     */
    @GetMapping("/api/policies/pricing/version/{version}")
    public ResponseEntity<PricingPolicy> getPolicyByVersion(@PathVariable String version) {
        PricingPolicy policy = policyManager.getPolicyByVersion(version);
        return ResponseEntity.ok(policy);
    }
    
    /**
     * Get all active policies
     */
    @GetMapping("/api/policies/pricing/active")
    public ResponseEntity<List<PricingPolicy>> getAllActive() {
        List<PricingPolicy> policies = policyManager.getAllActivePolicies();
        return ResponseEntity.ok(policies);
    }
    
    /**
     * Get all draft policies
     */
    @GetMapping("/api/policies/pricing/drafts")
    public ResponseEntity<List<PricingPolicy>> getAllDrafts() {
        List<PricingPolicy> policies = policyManager.getAllDraftPolicies();
        return ResponseEntity.ok(policies);
    }
}
