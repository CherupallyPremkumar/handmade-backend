package com.handmade.ecommerce.common.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.common.model.Common;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "commonService", serviceName = "_commonService_",
		healthCheckerName = "commonHealthChecker")
public class CommonController extends ControllerSupport{
	
    @PostMapping("/common")
    public ResponseEntity<GenericResponse<Common>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Common entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/common/{id}")
    public ResponseEntity<GenericResponse<Common>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
