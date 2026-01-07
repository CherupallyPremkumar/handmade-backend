package com.handmade.ecommerce.policy.configuration.controller;

import com.handmade.ecommerce.policy.domain.aggregate.PerformancePolicy;
import com.handmade.ecommerce.policy.api.PerformancePolicyManager;
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

@RestController
@ChenileController(value = "performancePolicyService", serviceName = "_performancePolicyStateEntityService_",
        healthCheckerName = "performancePolicyHealthChecker")
public class PerformancePolicyController extends ControllerSupport {
    
    @Autowired
    private PerformancePolicyManager policyManager;

    @GetMapping("/performance-policy/{id}")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<PerformancePolicy>>> retrieve(
            HttpServletRequest httpServletRequest,
            @PathVariable String id) {
        return process(httpServletRequest, id);
    }

    @PostMapping("/performance-policy")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<PerformancePolicy>>> create(
            HttpServletRequest httpServletRequest,
            @ChenileParamType(StateEntity.class)
            @RequestBody PerformancePolicy entity) {
        return process(httpServletRequest, entity);
    }

    @PatchMapping("/performance-policy/{id}/{eventID}")
    @BodyTypeSelector("performancePolicyBodyTypeSelector")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<PerformancePolicy>>> processById(
            HttpServletRequest httpServletRequest,
            @PathVariable String id,
            @PathVariable String eventID,
            @ChenileParamType(Object.class) 
            @RequestBody String eventPayload) {
        return process(httpServletRequest, id, eventID, eventPayload);
    }
}
