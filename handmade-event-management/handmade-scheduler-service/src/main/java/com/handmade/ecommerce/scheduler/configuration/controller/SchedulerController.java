package com.handmade.ecommerce.scheduler.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.scheduler.model.Scheduler;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "schedulerService", serviceName = "_schedulerService_",
		healthCheckerName = "schedulerHealthChecker")
public class SchedulerController extends ControllerSupport{
	
    @PostMapping("/scheduler")
    public ResponseEntity<GenericResponse<Scheduler>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Scheduler entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/scheduler/{id}")
    public ResponseEntity<GenericResponse<Scheduler>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
