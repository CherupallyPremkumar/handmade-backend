package com.homebase.ecom.configuration.controller;


import com.homebase.ecom.domain.Wishlist;
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
@ChenileController(value = "wishlistService", serviceName = "_wishlistStateEntityService_",
        healthCheckerName = "wishlistHealthChecker")
public class WishlistController extends ControllerSupport {

    @GetMapping("/wishlist/{id}")
    @InterceptedBy({"securityInterceptor"})
    public ResponseEntity<GenericResponse<StateEntityServiceResponse>> retrieve(
            HttpServletRequest httpServletRequest,
            @PathVariable String id) {
        return process("retrieve", httpServletRequest, id);
    }

    @PostMapping("/wishlist")
    @InterceptedBy({"securityInterceptor", "cloudEdgeSwitch"})
    public ResponseEntity<GenericResponse<StateEntityServiceResponse>> create(
            HttpServletRequest httpServletRequest,
            @ChenileParamType(StateEntity.class)
            @RequestBody Wishlist entity) {
        return process("create", httpServletRequest, entity);

    }


    @PutMapping("/wishlist/{id}/{eventID}")
    @BodyTypeSelector({"wishlistBodyTypeSelector", "subclassBodyTypeSelector"})
    @InterceptedBy({"securityInterceptor", "cloudEdgeSwitch"})
    public ResponseEntity<GenericResponse<StateEntityServiceResponse<Wishlist>>> processById(
            HttpServletRequest httpServletRequest,
            @PathVariable String id,
            @PathVariable String eventID,
            @ChenileParamType(Object.class)
            @RequestBody String eventPayload) {
        return process("processById", httpServletRequest, id, eventID, eventPayload);
    }
}

