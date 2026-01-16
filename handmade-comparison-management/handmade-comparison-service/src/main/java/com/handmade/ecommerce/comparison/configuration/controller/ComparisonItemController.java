package com.handmade.ecommerce.comparison.configuration.controller;

import com.handmade.ecommerce.comparison.model.ComparisonItem;
import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "comparisonItemService", serviceName = "_comparisonItemService_",
		healthCheckerName = "comparisonItemHealthChecker")
public class ComparisonItemController extends ControllerSupport{
	
    @PostMapping("/comparisonItem")
    public ResponseEntity<GenericResponse<ComparisonItem>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody ComparisonItem entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/comparisonItem/{id}")
    public ResponseEntity<GenericResponse<ComparisonItem>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
