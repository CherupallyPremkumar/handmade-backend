package com.handmade.ecommerce.deliveryexception.configuration.controller;

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
import com.handmade.ecommerce.logistics.model.DeliveryException;

@RestController
@ChenileController(value = "deliveryexceptionService", serviceName = "_deliveryexceptionStateEntityService_",
		healthCheckerName = "deliveryexceptionHealthChecker")
public class DeliveryExceptionController extends ControllerSupport{
	
	@GetMapping("/deliveryexception/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<DeliveryException>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/deliveryexception")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<DeliveryException>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody DeliveryException entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/deliveryexception/{id}/{eventID}")
	@BodyTypeSelector("deliveryexceptionBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<DeliveryException>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
