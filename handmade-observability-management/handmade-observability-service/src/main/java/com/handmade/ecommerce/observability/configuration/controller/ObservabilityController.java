package com.handmade.ecommerce.observability.configuration.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "observabilityService", serviceName = "_observabilityService_",
		healthCheckerName = "observabilityHealthChecker")
public class ObservabilityController extends ControllerSupport{
	
    @PostMapping("/observability")
    public ResponseEntity<GenericResponse<Observability>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Observability entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/observability/{id}")
    public ResponseEntity<GenericResponse<Observability>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
