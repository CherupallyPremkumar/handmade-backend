package com.handmade.ecommerce.seo.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.seo.model.Seo;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "seoService", serviceName = "_seoService_",
		healthCheckerName = "seoHealthChecker")
public class SeoController extends ControllerSupport{
	
    @PostMapping("/seo")
    public ResponseEntity<GenericResponse<Seo>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Seo entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/seo/{id}")
    public ResponseEntity<GenericResponse<Seo>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
