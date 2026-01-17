package com.handmade.ecommerce.orderline.configuration.controller;

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
import com.handmade.ecommerce.order.model.OrderLine;

@RestController
@ChenileController(value = "orderlineService", serviceName = "_orderlineStateEntityService_",
		healthCheckerName = "orderlineHealthChecker")
public class OrderLineController extends ControllerSupport{
	
	@GetMapping("/orderline/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<OrderLine>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/orderline")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<OrderLine>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody OrderLine entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/orderline/{id}/{eventID}")
	@BodyTypeSelector("orderlineBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<OrderLine>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
