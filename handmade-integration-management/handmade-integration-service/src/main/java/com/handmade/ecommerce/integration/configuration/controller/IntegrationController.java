package com.handmade.ecommerce.integration.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.integration.model.Integration;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "integrationService", serviceName = "_integrationService_",
		healthCheckerName = "integrationHealthChecker")
public class IntegrationController extends ControllerSupport{
	
    @PostMapping("/integration")
    public ResponseEntity<GenericResponse<Integration>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Integration entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/integration/{id}")
    public ResponseEntity<GenericResponse<Integration>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
