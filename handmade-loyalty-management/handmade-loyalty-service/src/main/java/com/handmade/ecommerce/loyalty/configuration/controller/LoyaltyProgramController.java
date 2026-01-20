package com.handmade.ecommerce.loyalty.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.loyalty.model.LoyaltyProgram;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "loyaltyService", serviceName = "_loyaltyService_",
		healthCheckerName = "loyaltyHealthChecker")
public class LoyaltyProgramController extends ControllerSupport{
	
    @PostMapping("/loyalty")
    public ResponseEntity<GenericResponse<LoyaltyProgram>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody LoyaltyProgram entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/loyalty/{id}")
    public ResponseEntity<GenericResponse<LoyaltyProgram>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
