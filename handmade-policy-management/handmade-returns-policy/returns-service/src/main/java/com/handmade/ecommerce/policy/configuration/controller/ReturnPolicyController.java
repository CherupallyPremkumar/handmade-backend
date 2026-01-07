package com.handmade.ecommerce.policy.configuration.controller;

import com.handmade.ecommerce.policy.domain.aggregate.ReturnPolicy;
import com.handmade.ecommerce.policy.api.ReturnPolicyManager;
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
@ChenileController(value = "returnPolicyService", serviceName = "_returnPolicyStateEntityService_",
        healthCheckerName = "returnPolicyHealthChecker")
public class ReturnPolicyController extends ControllerSupport {
    
    @Autowired
    private ReturnPolicyManager policyManager;

    @GetMapping("/return-policy/{id}")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<ReturnPolicy>>> retrieve(
            HttpServletRequest httpServletRequest,
            @PathVariable String id) {
        return process(httpServletRequest, id);
    }

    @PostMapping("/return-policy")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<ReturnPolicy>>> create(
            HttpServletRequest httpServletRequest,
            @ChenileParamType(StateEntity.class)
            @RequestBody ReturnPolicy entity) {
        return process(httpServletRequest, entity);
    }

    @PatchMapping("/return-policy/{id}/{eventID}")
    @BodyTypeSelector("returnPolicyBodyTypeSelector")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<ReturnPolicy>>> processById(
            HttpServletRequest httpServletRequest,
            @PathVariable String id,
            @PathVariable String eventID,
            @ChenileParamType(Object.class) 
            @RequestBody String eventPayload) {
        return process(httpServletRequest, id, eventID, eventPayload);
    }

    @GetMapping("/api/policies/return/resolve")
    public ResponseEntity<ReturnPolicy> resolvePolicy(
        @RequestParam String country,
        @RequestParam(required = false) String category
    ) {
        ReturnPolicy policy = policyManager.resolveActivePolicy(country, category, LocalDate.now());
        return ResponseEntity.ok(policy);
    }
}
