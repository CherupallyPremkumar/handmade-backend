package com.handmade.ecommerce.localization.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.localization.model.Localization;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "localizationService", serviceName = "_localizationService_",
		healthCheckerName = "localizationHealthChecker")
public class LocalizationController extends ControllerSupport{
	
    @PostMapping("/localization")
    public ResponseEntity<GenericResponse<Localization>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Localization entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/localization/{id}")
    public ResponseEntity<GenericResponse<Localization>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
