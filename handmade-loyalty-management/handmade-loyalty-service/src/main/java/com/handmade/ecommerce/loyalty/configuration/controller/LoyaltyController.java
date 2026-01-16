package com.handmade.ecommerce.loyalty.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.loyalty.model.Loyalty;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "loyaltyService", serviceName = "_loyaltyService_",
		healthCheckerName = "loyaltyHealthChecker")
public class LoyaltyController extends ControllerSupport{
	
    @PostMapping("/loyalty")
    public ResponseEntity<GenericResponse<Loyalty>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Loyalty entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/loyalty/{id}")
    public ResponseEntity<GenericResponse<Loyalty>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
