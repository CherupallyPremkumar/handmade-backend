package com.handmade.ecommerce.policy.configuration.controller;

import com.handmade.ecommerce.policy.domain.aggregate.InventoryPolicy;
import com.handmade.ecommerce.policy.api.InventoryPolicyManager;
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
@ChenileController(value = "inventoryPolicyService", serviceName = "_inventoryPolicyStateEntityService_",
        healthCheckerName = "inventoryPolicyHealthChecker")
public class InventoryPolicyController extends ControllerSupport {
    
    @Autowired
    private InventoryPolicyManager policyManager;

    @GetMapping("/inventory-policy/{id}")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<InventoryPolicy>>> retrieve(
            HttpServletRequest httpServletRequest,
            @PathVariable String id) {
        return process(httpServletRequest, id);
    }

    @PostMapping("/inventory-policy")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<InventoryPolicy>>> create(
            HttpServletRequest httpServletRequest,
            @ChenileParamType(StateEntity.class)
            @RequestBody InventoryPolicy entity) {
        return process(httpServletRequest, entity);
    }

    @PatchMapping("/inventory-policy/{id}/{eventID}")
    @BodyTypeSelector("inventoryPolicyBodyTypeSelector")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<InventoryPolicy>>> processById(
            HttpServletRequest httpServletRequest,
            @PathVariable String id,
            @PathVariable String eventID,
            @ChenileParamType(Object.class) 
            @RequestBody String eventPayload) {
        return process(httpServletRequest, id, eventID, eventPayload);
    }

    @GetMapping("/api/policies/inventory/resolve")
    public ResponseEntity<InventoryPolicy> resolvePolicy(
        @RequestParam String country,
        @RequestParam(required = false) String category
    ) {
        InventoryPolicy policy = policyManager.resolveActivePolicy(country, category, LocalDate.now());
        return ResponseEntity.ok(policy);
    }
}
