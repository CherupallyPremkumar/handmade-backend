package com.handmade.ecommerce.policy.configuration.controller;

import com.handmade.ecommerce.policy.domain.aggregate.FraudPolicy;
import com.handmade.ecommerce.policy.api.FraudPolicyManager;
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
@ChenileController(value = "fraudPolicyService", serviceName = "_fraudPolicyStateEntityService_",
        healthCheckerName = "fraudPolicyHealthChecker")
public class FraudPolicyController extends ControllerSupport {
    
    @Autowired
    private FraudPolicyManager policyManager;

    @GetMapping("/fraud-policy/{id}")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<FraudPolicy>>> retrieve(
            HttpServletRequest httpServletRequest,
            @PathVariable String id) {
        return process(httpServletRequest, id);
    }

    @PostMapping("/fraud-policy")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<FraudPolicy>>> create(
            HttpServletRequest httpServletRequest,
            @ChenileParamType(StateEntity.class)
            @RequestBody FraudPolicy entity) {
        return process(httpServletRequest, entity);
    }

    @PatchMapping("/fraud-policy/{id}/{eventID}")
    @BodyTypeSelector("fraudPolicyBodyTypeSelector")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<FraudPolicy>>> processById(
            HttpServletRequest httpServletRequest,
            @PathVariable String id,
            @PathVariable String eventID,
            @ChenileParamType(Object.class) 
            @RequestBody String eventPayload) {
        return process(httpServletRequest, id, eventID, eventPayload);
    }
}
