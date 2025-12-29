package com.handmade.ecommerce.ledger.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.ledger.model.Ledger;
import org.chenile.security.model.SecurityConfig;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "ledgerService", serviceName = "_ledgerService_",
		healthCheckerName = "ledgerHealthChecker")
public class LedgerController extends ControllerSupport{
	
    @PostMapping("/ledger")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Ledger>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Ledger entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/ledger/{id}")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Ledger>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
