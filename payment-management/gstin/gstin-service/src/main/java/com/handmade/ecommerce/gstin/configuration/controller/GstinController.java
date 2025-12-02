package com.handmade.ecommerce.gstin.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.gstin.model.Gstin;
import org.chenile.security.model.SecurityConfig;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "gstinService", serviceName = "_gstinService_",
		healthCheckerName = "gstinHealthChecker")
public class GstinController extends ControllerSupport{
	
    @PostMapping("/gstin")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Gstin>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Gstin entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/gstin/{id}")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Gstin>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
