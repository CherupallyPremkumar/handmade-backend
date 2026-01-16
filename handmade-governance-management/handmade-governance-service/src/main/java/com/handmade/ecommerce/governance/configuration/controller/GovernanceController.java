package com.handmade.ecommerce.governance.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import org.chenile.base.response.GenericResponse;
import com.handmade.ecommerce.governance.model.Governance;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@ChenileController(value = "governanceService", serviceName = "_governanceService_",
		healthCheckerName = "governanceHealthChecker")
public class GovernanceController extends ControllerSupport{
	
    @PostMapping("/governance")
    public ResponseEntity<GenericResponse<Governance>> save(
        HttpServletRequest httpServletRequest,
        @RequestBody Governance entity){
        return process(httpServletRequest,entity);
        }

    @GetMapping("/governance/{id}")
    public ResponseEntity<GenericResponse<Governance>> retrieve(
    HttpServletRequest httpServletRequest,
    @PathVariable("id") String id){
    return process(httpServletRequest,id);
    }
}
