package com.handmade.ecommerce.shipping.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.shipping.model.Shipping;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "shippingService", serviceName = "_shippingService_",
		healthCheckerName = "shippingHealthChecker")
public class ShippingController extends ControllerSupport{
	
    @PostMapping("/shipping")
    public ResponseEntity<GenericResponse<Shipping>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Shipping entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/shipping/{id}")
    public ResponseEntity<GenericResponse<Shipping>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
