package com.handmade.ecommerce.deliveryattempt.configuration.controller;

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
import com.handmade.ecommerce.logistics.model.DeliveryAttempt;

@RestController
@ChenileController(value = "deliveryattemptService", serviceName = "_deliveryattemptStateEntityService_",
		healthCheckerName = "deliveryattemptHealthChecker")
public class DeliveryAttemptController extends ControllerSupport{
	
	@GetMapping("/deliveryattempt/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<DeliveryAttempt>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/deliveryattempt")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<DeliveryAttempt>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody DeliveryAttempt entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/deliveryattempt/{id}/{eventID}")
	@BodyTypeSelector("deliveryattemptBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<DeliveryAttempt>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
