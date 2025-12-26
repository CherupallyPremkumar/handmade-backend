package com.handmade.ecommerce.orchestrator.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.orchestrator.model.Orchestrator;
import org.chenile.security.model.SecurityConfig;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "orchestratorService", serviceName = "_orchestratorService_",
		healthCheckerName = "orchestratorHealthChecker")
public class OrchestratorController extends ControllerSupport{
	
    @PostMapping("/orchestrator")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Orchestrator>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Orchestrator entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/orchestrator/{id}")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Orchestrator>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
