package com.handmade.ecommerce.wallet.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.wallet.model.Wallet;
import org.chenile.security.model.SecurityConfig;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "walletService", serviceName = "_walletService_",
		healthCheckerName = "walletHealthChecker")
public class WalletController extends ControllerSupport{
	
    @PostMapping("/wallet")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Wallet>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Wallet entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/wallet/{id}")
    @SecurityConfig(authorities = {"some_premium_scope","test.premium"})
    public ResponseEntity<GenericResponse<Wallet>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
