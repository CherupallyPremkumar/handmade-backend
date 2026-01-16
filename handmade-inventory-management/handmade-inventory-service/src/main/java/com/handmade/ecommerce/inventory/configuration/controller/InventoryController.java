package com.handmade.ecommerce.inventory.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.inventory.model.Inventory;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "inventoryService", serviceName = "_inventoryService_",
		healthCheckerName = "inventoryHealthChecker")
public class InventoryController extends ControllerSupport{
	
    @PostMapping("/inventory")
    public ResponseEntity<GenericResponse<Inventory>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Inventory entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/inventory/{id}")
    public ResponseEntity<GenericResponse<Inventory>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
