package com.handmade.ecommerce.search.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.search.model.Search;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "searchService", serviceName = "_searchService_",
		healthCheckerName = "searchHealthChecker")
public class SearchController extends ControllerSupport{
	
    @PostMapping("/search")
    public ResponseEntity<GenericResponse<Search>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Search entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/search/{id}")
    public ResponseEntity<GenericResponse<Search>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
