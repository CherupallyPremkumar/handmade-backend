package com.handmade.ecommerce.stripe.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.stripe.model.Stripe;
import org.chenile.security.model.SecurityConfig;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "stripeService", serviceName = "_stripeService_",
		healthCheckerName = "stripeHealthChecker")
public class StripeController extends ControllerSupport{
	
    @PostMapping("/stripe")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Stripe>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Stripe entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/stripe/{id}")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Stripe>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
