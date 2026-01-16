package com.handmade.ecommerce.subscription.configuration.controller;

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
import com.handmade.ecommerce.customer.model.Subscription;

@RestController
@ChenileController(value = "subscriptionService", serviceName = "_subscriptionStateEntityService_",
		healthCheckerName = "subscriptionHealthChecker")
public class SubscriptionController extends ControllerSupport{
	
	@GetMapping("/subscription/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Subscription>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/subscription")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Subscription>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Subscription entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/subscription/{id}/{eventID}")
	@BodyTypeSelector("subscriptionBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Subscription>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
