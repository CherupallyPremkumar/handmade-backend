package com.handmade.ecommerce.customer.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.customer.model.Customer;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "customerService", serviceName = "_customerService_",
		healthCheckerName = "customerHealthChecker")
public class CustomerController extends ControllerSupport{
	
    @PostMapping("/customer")
    public ResponseEntity<GenericResponse<Customer>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Customer entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/customer/{id}")
    public ResponseEntity<GenericResponse<Customer>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
