package com.homebase.ecom.configuration.controller;


import com.homebase.ecom.domain.Order;
import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.BodyTypeSelector;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.ChenileParamType;
import org.chenile.http.annotation.InterceptedBy;
import org.chenile.http.handler.ControllerSupport;
import org.chenile.stm.StateEntity;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "orderService", serviceName = "_orderStateEntityService_",
        healthCheckerName = "orderHealthChecker")
public class OrderController extends ControllerSupport {

    @GetMapping("/order/{id}")
    @InterceptedBy({"securityInterceptor"})
    public ResponseEntity<GenericResponse<StateEntityServiceResponse>> retrieve(
            HttpServletRequest httpServletRequest,
            @PathVariable String id) {
        return process("retrieve", httpServletRequest, id);
    }

    @PostMapping("/order")
    @InterceptedBy({"securityInterceptor"})
    public ResponseEntity<GenericResponse<StateEntityServiceResponse>> create(
            HttpServletRequest httpServletRequest,
            @ChenileParamType(StateEntity.class)
            @RequestBody Order entity) {
        return process("create", httpServletRequest, entity);

    }


    @PutMapping("/order/{id}/{eventID}")
    @BodyTypeSelector({"orderBodyTypeSelector", "subclassBodyTypeSelector"})
    @InterceptedBy({"securityInterceptor"})
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<Order>>> processById(
            HttpServletRequest httpServletRequest,
            @PathVariable String id,
            @PathVariable String eventID,
            @ChenileParamType(Object.class)
            @RequestBody String eventPayload) {
        return process("processById", httpServletRequest, id, eventID, eventPayload);
    }
}
