package com.handmade.ecommerce.messaging.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.messaging.model.Messaging;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "messagingService", serviceName = "_messagingService_",
		healthCheckerName = "messagingHealthChecker")
public class MessagingController extends ControllerSupport{
	
    @PostMapping("/messaging")
    public ResponseEntity<GenericResponse<Messaging>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Messaging entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/messaging/{id}")
    public ResponseEntity<GenericResponse<Messaging>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
