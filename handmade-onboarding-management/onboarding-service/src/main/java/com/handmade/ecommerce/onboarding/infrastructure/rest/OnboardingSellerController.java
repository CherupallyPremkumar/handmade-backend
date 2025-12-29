package com.handmade.ecommerce.onboarding.infrastructure.rest;

import com.handmade.ecommerce.onboarding.domain.aggregate.SellerOnboardingCase;
import com.handmade.ecommerce.onboarding.dto.BankUpdateRequest;
import com.handmade.ecommerce.onboarding.dto.IdentityUpdateRequest;
import com.handmade.ecommerce.identity.dto.IdentityVerificationView;
import com.handmade.ecommerce.onboarding.dto.TaxUpdateRequest;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.BodyTypeSelector;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.ChenileParamType;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.chenile.stm.StateEntity;
import org.springframework.web.bind.annotation.*;
import org.chenile.workflow.dto.StateEntityServiceResponse;

@RestController
@ChenileController(value = "onboardingSellerService", serviceName = "_onboardingSellerStateEntityService_", healthCheckerName = "onboardingSellerHealthChecker")
public class OnboardingSellerController extends ControllerSupport {

    @GetMapping("/onboarding-seller/{id}")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<SellerOnboardingCase>>> retrieve(
            HttpServletRequest httpServletRequest,
            @PathVariable String id) {
        return process(httpServletRequest, id);
    }

    @PostMapping("/onboarding-seller")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<SellerOnboardingCase>>> create(
            HttpServletRequest httpServletRequest,
            @ChenileParamType(StateEntity.class) @RequestBody SellerOnboardingCase entity) {
        return process(httpServletRequest, entity);
    }

    @PatchMapping("/onboarding-seller/{id}/{eventID}")
    @BodyTypeSelector("onboardingSellerBodyTypeSelector")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<SellerOnboardingCase>>> processById(
            HttpServletRequest httpServletRequest,
            @PathVariable String id,
            @PathVariable String eventID,
            @ChenileParamType(Object.class) @RequestBody String eventPayload) {
        return process(httpServletRequest, id, eventID, eventPayload);
    }

    @PostMapping("/onboarding/cases/{caseId}/identity")
    public ResponseEntity<IdentityVerificationView> updateIdentity(
            HttpServletRequest httpServletRequest,
            @PathVariable String caseId,
            @RequestBody IdentityUpdateRequest request) {
        process("updateIdentity", httpServletRequest, caseId, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/onboarding/cases/{caseId}/tax")
    public ResponseEntity<Void> updateTax(
            HttpServletRequest httpServletRequest,
            @PathVariable String caseId,
            @RequestBody TaxUpdateRequest request) {
        process("updateTax", httpServletRequest, caseId, request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/onboarding/cases/{caseId}/bank")
    public ResponseEntity<Void> updateBank(
            HttpServletRequest httpServletRequest,
            @PathVariable String caseId,
            @RequestBody BankUpdateRequest request) {
        process("updateBank", httpServletRequest, caseId, request);
        return ResponseEntity.noContent().build();
    }

    /**
     * Creates a Stripe Identity verification session.
     * Does NOT trigger STM transition - webhook will do that.
     */
    @PostMapping("/onboarding/cases/{caseId}/identity/session")
    public ResponseEntity<GenericResponse<IdentityVerificationView>> createIdentitySession(
            HttpServletRequest httpServletRequest,
            @PathVariable String caseId) {
        return process("createIdentitySession", httpServletRequest, caseId);
    }
}
