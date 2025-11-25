package com.handmade.ecommerce.cash.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.cash.model.Cash;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "cashService", serviceName = "_cashService_",
		healthCheckerName = "cashHealthChecker")
public class CashController extends ControllerSupport{
	
    @PostMapping("/cash")
    public ResponseEntity<GenericResponse<Cash>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Cash entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/cash/{id}")
    public ResponseEntity<GenericResponse<Cash>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
