package com.homebase.ecom.configuration.controller;


import com.homebase.ecom.domain.CartItem;
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
@ChenileController(value = "cartItemService", serviceName = "_cartItemStateEntityService_",
        healthCheckerName = "cartItemHealthChecker")
public class CartItemController extends ControllerSupport {

    @GetMapping("/cartItem/{id}")
    @InterceptedBy({"securityInterceptor"})
    public ResponseEntity<GenericResponse<StateEntityServiceResponse>> retrieve(
            HttpServletRequest httpServletRequest,
            @PathVariable String id) {
        return process("retrieve", httpServletRequest, id);
    }

    @PostMapping("/cartItem")
    @InterceptedBy({"securityInterceptor", "cloudEdgeSwitch"})
    public ResponseEntity<GenericResponse<StateEntityServiceResponse>> create(
            HttpServletRequest httpServletRequest,
            @ChenileParamType(StateEntity.class)
            @RequestBody CartItem entity) {
        return process("create", httpServletRequest, entity);

    }


    @PutMapping("/cartItem/{id}/{eventID}")
    @BodyTypeSelector({"cartItemBodyTypeSelector", "subclassBodyTypeSelector"})
    @InterceptedBy({"securityInterceptor", "cloudEdgeSwitch"})
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<CartItem>>> processById(
            HttpServletRequest httpServletRequest,
            @PathVariable String id,
            @PathVariable String eventID,
            @ChenileParamType(Object.class)
            @RequestBody String eventPayload) {
        return process("processById", httpServletRequest, id, eventID, eventPayload);
    }
}
