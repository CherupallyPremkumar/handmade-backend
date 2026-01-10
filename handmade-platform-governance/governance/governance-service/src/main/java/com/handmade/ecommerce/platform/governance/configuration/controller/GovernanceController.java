package com.handmade.ecommerce.platform.governance.configuration.controller;

import com.handmade.ecommerce.platform.governance.entity.GdprRequest;
import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.BodyTypeSelector;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.ChenileParamType;
import org.chenile.http.handler.ControllerSupport;
import org.chenile.stm.StateEntity;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@ChenileController(value = "governanceService", serviceName = "_governanceService_", healthCheckerName = "governanceHealthChecker")
public class GovernanceController extends ControllerSupport {

    @PostMapping("/governance")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<GdprRequest>>> create(
            HttpServletRequest httpServletRequest,
            @ChenileParamType(StateEntity.class) @RequestBody GdprRequest entity) {
        return process(httpServletRequest, entity);
    }

    @PatchMapping("/governance/{id}/{eventID}")
    @BodyTypeSelector("governanceBodyTypeSelector")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<GdprRequest>>> processById(
            HttpServletRequest httpServletRequest,
            @PathVariable String id,
            @PathVariable String eventID,
            @ChenileParamType(Object.class) @RequestBody String eventPayload) {
        return process(httpServletRequest, id, eventID, eventPayload);
    }

    @GetMapping("/governance/{id}")
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<GdprRequest>>> retrieve(
            HttpServletRequest httpServletRequest,
            @PathVariable String id) {
        return process(httpServletRequest, id);
    }

}
