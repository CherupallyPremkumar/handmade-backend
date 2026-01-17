package com.handmade.ecommerce.routeplan.configuration.controller;

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
import com.handmade.ecommerce.logistics.model.RoutePlan;

@RestController
@ChenileController(value = "routeplanService", serviceName = "_routeplanStateEntityService_",
		healthCheckerName = "routeplanHealthChecker")
public class RoutePlanController extends ControllerSupport{
	
	@GetMapping("/routeplan/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<RoutePlan>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/routeplan")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<RoutePlan>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody RoutePlan entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/routeplan/{id}/{eventID}")
	@BodyTypeSelector("routeplanBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<RoutePlan>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
