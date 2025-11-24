package com.handmade.ecommerce.events.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.events.model.Events;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "eventsService", serviceName = "_eventsService_",
		healthCheckerName = "eventsHealthChecker")
public class EventsController extends ControllerSupport{
	
    @PostMapping("/events")
    public ResponseEntity<GenericResponse<Events>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Events entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/events/{id}")
    public ResponseEntity<GenericResponse<Events>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
