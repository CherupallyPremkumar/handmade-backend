package com.handmade.ecommerce.seo.configuration.controller;

import java.util.Map;

import com.handmade.ecommerce.seo.model.MetaTag;
import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "metaTagService", serviceName = "_metaTagService_",
		healthCheckerName = "metaTagHealthChecker")
public class MetaTagController extends ControllerSupport{
	
    @PostMapping("/metaTag")
    public ResponseEntity<GenericResponse<MetaTag>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody MetaTag entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/metaTag/{id}")
    public ResponseEntity<GenericResponse<MetaTag>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
