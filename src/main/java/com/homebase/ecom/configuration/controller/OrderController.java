package com.netscoretech.pos.order.configuration.controller;

import com.netscoretech.pos.order.model.Order;
import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import org.chenile.cloudedgeswitch.CloudEdgeSwitchConfig;
import org.chenile.http.annotation.BodyTypeSelector;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.ChenileParamType;
import org.chenile.http.annotation.InterceptedBy;
import org.chenile.http.handler.ControllerSupport;
import org.chenile.mqtt.model.ChenileMqtt;
import org.chenile.security.model.SecurityConfig;
import org.chenile.stm.StateEntity;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ChenileMqtt
@ChenileController(value = "orderService", serviceName = "_orderStateEntityService_",
        healthCheckerName = "orderHealthChecker")
public class OrderController extends ControllerSupport {

    @GetMapping("/order/{id}")
    @InterceptedBy({"securityInterceptor"})
    @SecurityConfig(authorities = {"test.premium", "test.normal"})
    public ResponseEntity<GenericResponse<StateEntityServiceResponse>> retrieve(
            HttpServletRequest httpServletRequest,
            @PathVariable String id) {
        return process("retrieve", httpServletRequest, id);
    }

    @PostMapping("/order")
    @InterceptedBy({"securityInterceptor", "cloudEdgeSwitch"})
    @SecurityConfig(authorities = {"test.premium", "test.normal"})
    @CloudEdgeSwitchConfig
    public ResponseEntity<GenericResponse<StateEntityServiceResponse>> create(
            HttpServletRequest httpServletRequest,
            @ChenileParamType(StateEntity.class)
            @RequestBody Order entity) {
        return process("create", httpServletRequest, entity);

    }


    @PutMapping("/order/{id}/{eventID}")
    @BodyTypeSelector({"orderBodyTypeSelector", "subclassBodyTypeSelector"})
    @InterceptedBy({"securityInterceptor", "cloudEdgeSwitch"})
    @CloudEdgeSwitchConfig
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<Order>>> processById(
            HttpServletRequest httpServletRequest,
            @PathVariable String id,
            @PathVariable String eventID,
            @ChenileParamType(Object.class)
            @RequestBody String eventPayload) {
        return process("processById", httpServletRequest, id, eventID, eventPayload);
    }
}
