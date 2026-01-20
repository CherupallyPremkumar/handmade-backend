package com.handmade.ecommerce.tax.configuration.controller;

import java.util.Map;

import com.handmade.ecommerce.tax.model.TaxRate;
import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "taxRateService", serviceName = "_taxRateService_",
		healthCheckerName = "taxRateHealthChecker")
public class TaxRateController extends ControllerSupport{
	
    @PostMapping("/taxRate")
    public ResponseEntity<GenericResponse<TaxRate>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody TaxRate entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/taxRate/{id}")
    public ResponseEntity<GenericResponse<TaxRate>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
