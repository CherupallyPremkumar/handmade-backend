package com.handmade.ecommerce.reconciliation.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.reconciliation.model.Reconciliation;
import org.chenile.security.model.SecurityConfig;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "reconciliationService", serviceName = "_reconciliationService_",
		healthCheckerName = "reconciliationHealthChecker")
public class ReconciliationController extends ControllerSupport{
	
    @PostMapping("/reconciliation")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Reconciliation>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Reconciliation entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/reconciliation/{id}")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Reconciliation>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
