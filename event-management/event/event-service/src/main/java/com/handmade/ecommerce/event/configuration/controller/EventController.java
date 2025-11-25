package com.handmade.ecommerce.event.configuration.controller;


import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "eventService", serviceName = "_eventService_",
		healthCheckerName = "eventHealthChecker")
public class EventController extends ControllerSupport{
	

}
