package com.handmade.ecommerce.razorpay.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.razorpay.model.Razorpay;
import org.chenile.security.model.SecurityConfig;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "razorpayService", serviceName = "_razorpayService_",
		healthCheckerName = "razorpayHealthChecker")
public class RazorpayController extends ControllerSupport{
	
    @PostMapping("/razorpay")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Razorpay>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Razorpay entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/razorpay/{id}")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Razorpay>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
