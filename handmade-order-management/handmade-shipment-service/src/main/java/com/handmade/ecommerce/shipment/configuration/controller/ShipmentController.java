package com.handmade.ecommerce.shipment.configuration.controller;

import com.handmade.ecommerce.order.model.Shipment;
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

@RestController
@ChenileController(value = "shipmentService", serviceName = "_shipmentStateEntityService_",
		healthCheckerName = "shipmentHealthChecker")
public class ShipmentController extends ControllerSupport{
	
	@GetMapping("/shipment/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Shipment>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/shipment")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Shipment>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Shipment entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/shipment/{id}/{eventID}")
	@BodyTypeSelector("shipmentBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Shipment>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
