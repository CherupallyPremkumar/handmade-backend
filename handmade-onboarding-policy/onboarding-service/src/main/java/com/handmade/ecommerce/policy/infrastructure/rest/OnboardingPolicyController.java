package com.handmade.ecommerce.policy.infrastructure.rest;

import com.handmade.ecommerce.policy.domain.aggregate.OnboardingPolicy;
import com.handmade.ecommerce.policy.api.OnboardingPolicyManager;
import com.handmade.ecommerce.platform.domain.enums.SellerType;
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
 * Enterprise REST API for onboarding policy management.
 * Follows the PlatformController pattern with Chenile STM support.
 */
@RestController
@ChenileController(value = "onboardingPolicyService", serviceName = "_onboardingPolicyStateEntityService_",
        healthCheckerName = "onboardingPolicyHealthChecker")
public class OnboardingPolicyController extends ControllerSupport {
    
    @Autowired
    private OnboardingPolicyManager policyManager;

    // ========== GENERIC CRUD & WORKFLOW (Chenile Pattern) ==========

    @GetMapping("/onboarding-policy/{id}")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<OnboardingPolicy>>> retrieve(
            HttpServletRequest httpServletRequest,
            @PathVariable String id) {
        return process(httpServletRequest, id);
    }

    @PostMapping("/onboarding-policy")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<OnboardingPolicy>>> create(
            HttpServletRequest httpServletRequest,
            @ChenileParamType(StateEntity.class)
            @RequestBody OnboardingPolicy entity) {
        return process(httpServletRequest, entity);
    }

    @PatchMapping("/onboarding-policy/{id}/{eventID}")
    @BodyTypeSelector("onboardingPolicyBodyTypeSelector")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<OnboardingPolicy>>> processById(
            HttpServletRequest httpServletRequest,
            @PathVariable String id,
            @PathVariable String eventID,
            @ChenileParamType(Object.class) 
            @RequestBody String eventPayload) {
        return process(httpServletRequest, id, eventID, eventPayload);
    }

    // ========== BUSINESS SPECIFIC ENDPOINTS ==========
    
    /**
     * Resolve active policy for country and seller type
     */
    @GetMapping("/api/policies/onboarding/resolve")
    public ResponseEntity<OnboardingPolicy> resolvePolicy(
        @RequestParam String country,
        @RequestParam SellerType sellerType
    ) {
        OnboardingPolicy policy = policyManager.resolveActivePolicy(country, sellerType, LocalDate.now());
        return ResponseEntity.ok(policy);
    }
    
    /**
     * Get policy by version (for locked sellers)
     */
    @GetMapping("/api/policies/onboarding/{version}")
    public ResponseEntity<OnboardingPolicy> getPolicyByVersion(@PathVariable String version) {
        OnboardingPolicy policy = policyManager.getPolicyByVersion(version);
        return ResponseEntity.ok(policy);
    }
    
    /**
     * Check if onboarding is available for country
     */
    @GetMapping("/api/policies/onboarding/available")
    public ResponseEntity<Boolean> checkAvailability(
        @RequestParam String country,
        @RequestParam SellerType sellerType
    ) {
        boolean available = policyManager.canOnboardInCountry(country, sellerType);
        return ResponseEntity.ok(available);
    }
    
    /**
     * Get all active policies (admin)
     */
    @GetMapping("/api/policies/onboarding/active")
    public ResponseEntity<List<OnboardingPolicy>> getAllActive() {
        List<OnboardingPolicy> policies = policyManager.getAllActivePolicies();
        return ResponseEntity.ok(policies);
    }
    
    /**
     * Get all draft policies (admin)
     */
    @GetMapping("/api/policies/onboarding/drafts")
    public ResponseEntity<List<OnboardingPolicy>> getAllDrafts() {
        List<OnboardingPolicy> policies = policyManager.getAllDraftPolicies();
        return ResponseEntity.ok(policies);
    }
}
