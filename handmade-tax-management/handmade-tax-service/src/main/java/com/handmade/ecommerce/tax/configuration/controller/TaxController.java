package com.handmade.ecommerce.tax.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.tax.model.Tax;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "taxService", serviceName = "_taxService_",
		healthCheckerName = "taxHealthChecker")
public class TaxController extends ControllerSupport{
	
    @PostMapping("/tax")
    public ResponseEntity<GenericResponse<Tax>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Tax entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/tax/{id}")
    public ResponseEntity<GenericResponse<Tax>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
