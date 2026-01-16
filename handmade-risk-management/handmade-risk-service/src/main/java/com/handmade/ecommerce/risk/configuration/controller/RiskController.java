package com.handmade.ecommerce.risk.configuration.controller;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.chenile.base.response.GenericResponse;
import org.chenile.http.annotation.BodyTypeSelector;
import org.chenile.http.annotation.ChenileController;
import org.chenile.http.annotation.ChenileParamType;
import org.chenile.http.handler.ControllerSupport;
import org.springframework.http.ResponseEntity;

import org.chenile.stm.StateEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.chenile.workflow.dto.StateEntityServiceResponse;
import com.handmade.ecommerce.risk.model.Risk;

@RestController
@ChenileController(value = "riskService", serviceName = "_riskStateEntityService_",
		healthCheckerName = "riskHealthChecker")
public class RiskController extends ControllerSupport{
	
	@GetMapping("/risk/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Risk>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/risk")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Risk>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Risk entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/risk/{id}/{eventID}")
	@BodyTypeSelector("riskBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Risk>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
