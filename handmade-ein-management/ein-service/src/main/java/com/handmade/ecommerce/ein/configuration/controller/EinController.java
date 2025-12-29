package com.handmade.ecommerce.ein.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.ein.model.Ein;
import org.chenile.security.model.SecurityConfig;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "einService", serviceName = "_einService_",
		healthCheckerName = "einHealthChecker")
public class EinController extends ControllerSupport{
	
    @PostMapping("/ein")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Ein>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Ein entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/ein/{id}")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Ein>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
