package com.handmade.ecommerce.catalog.configuration.controller;

import com.handmade.ecommerce.catalog.model.CatalogItem;
import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@ChenileController(value = "catalogItemService", serviceName = "_catalogItemService_",
		healthCheckerName = "catalogItemHealthChecker")
public class CatalogItemController extends ControllerSupport{
	
    @PostMapping("/catalogItem")
    public ResponseEntity<GenericResponse<CatalogItem>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody CatalogItem entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/catalogItem/{id}")
    public ResponseEntity<GenericResponse<CatalogItem>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
