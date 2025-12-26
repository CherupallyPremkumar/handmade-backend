package com.handmade.ecommerce.location.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.location.model.Location;
import org.chenile.security.model.SecurityConfig;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "locationService", serviceName = "_locationService_",
		healthCheckerName = "locationHealthChecker")
public class LocationController extends ControllerSupport{
	
    @PostMapping("/location")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Location>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Location entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/location/{id}")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Location>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
