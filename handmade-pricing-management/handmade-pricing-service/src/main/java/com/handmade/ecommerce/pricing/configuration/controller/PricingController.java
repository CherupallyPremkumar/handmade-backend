package com.handmade.ecommerce.pricing.configuration.controller;

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
import com.handmade.ecommerce.pricing.model.Pricing;

@RestController
@ChenileController(value = "pricingService", serviceName = "_pricingStateEntityService_",
		healthCheckerName = "pricingHealthChecker")
public class PricingController extends ControllerSupport{
	
	@GetMapping("/pricing/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Pricing>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/pricing")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Pricing>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Pricing entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/pricing/{id}/{eventID}")
	@BodyTypeSelector("pricingBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Pricing>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
