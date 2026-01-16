package com.handmade.ecommerce.analytics.configuration.controller;

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
import com.handmade.ecommerce.analytics.model.MetricDefinition;

@RestController
@ChenileController(value = "analyticsService", serviceName = "_analyticsStateEntityService_",
		healthCheckerName = "analyticsHealthChecker")
public class MetricDefinitionController extends ControllerSupport{
	
	@GetMapping("/analytics/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<MetricDefinition>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/analytics")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<MetricDefinition>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody MetricDefinition entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/analytics/{id}/{eventID}")
	@BodyTypeSelector("analyticsBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<MetricDefinition>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
