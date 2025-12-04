package com.handmade.ecommerce.orchestrator.configuration.controller;

import com.handmade.ecommerce.command.CreatePaymentRequest;
import com.handmade.ecommerce.command.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.orchestrator.model.Orchestrator;
import org.chenile.security.model.SecurityConfig;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@ChenileController(value = "productOrchestratorService", serviceName = "_productOrchestratorService_",
		healthCheckerName = "productOrchestratorHealthChecker")
public class ProductOrchestratorController extends ControllerSupport{

    private static final Logger log = Logger.getLogger(ProductOrchestratorController.class.getName());


    @PostMapping("/v1/payment")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<PaymentResponse>> createPayment(
            HttpServletRequest httpServletRequest,
            @RequestBody CreatePaymentRequest request){

        log.info("Processing payment for checkoutId: {}, buyer: {}, orders: {}"
        );
        try {
            return process(httpServletRequest,request);
        } catch (Exception e) {
            log.severe("Error processing payment: " + e.getMessage());
            throw e;
        }
    }
}
