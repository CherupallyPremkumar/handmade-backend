package com.handmade.ecommerce.policy.configuration.controller;

import com.handmade.ecommerce.policy.domain.aggregate.ListingPolicy;
import com.handmade.ecommerce.policy.api.ListingPolicyManager;
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

@RestController
@ChenileController(value = "listingPolicyService", serviceName = "_listingPolicyStateEntityService_",
        healthCheckerName = "listingPolicyHealthChecker")
public class ListingPolicyController extends ControllerSupport {
    
    @Autowired
    private ListingPolicyManager policyManager;

    @GetMapping("/listing-policy/{id}")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<ListingPolicy>>> retrieve(
            HttpServletRequest httpServletRequest,
            @PathVariable String id) {
        return process(httpServletRequest, id);
    }

    @PostMapping("/listing-policy")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<ListingPolicy>>> create(
            HttpServletRequest httpServletRequest,
            @ChenileParamType(StateEntity.class)
            @RequestBody ListingPolicy entity) {
        return process(httpServletRequest, entity);
    }

    @PatchMapping("/listing-policy/{id}/{eventID}")
    @BodyTypeSelector("listingPolicyBodyTypeSelector")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<ListingPolicy>>> processById(
            HttpServletRequest httpServletRequest,
            @PathVariable String id,
            @PathVariable String eventID,
            @ChenileParamType(Object.class) 
            @RequestBody String eventPayload) {
        return process(httpServletRequest, id, eventID, eventPayload);
    }

    @GetMapping("/api/policies/listing/resolve")
    public ResponseEntity<ListingPolicy> resolvePolicy(
        @RequestParam String country,
        @RequestParam(required = false) String category
    ) {
        ListingPolicy policy = policyManager.resolveActivePolicy(country, category, LocalDate.now());
        return ResponseEntity.ok(policy);
    }
}
