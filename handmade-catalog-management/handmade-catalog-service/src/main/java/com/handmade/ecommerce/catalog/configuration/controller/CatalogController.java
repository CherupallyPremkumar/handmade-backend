package com.handmade.ecommerce.catalog.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.catalog.model.Catalog;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "catalogService", serviceName = "_catalogService_",
		healthCheckerName = "catalogHealthChecker")
public class CatalogController extends ControllerSupport{
	
    @PostMapping("/catalog")
    public ResponseEntity<GenericResponse<Catalog>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Catalog entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/catalog/{id}")
    public ResponseEntity<GenericResponse<Catalog>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
