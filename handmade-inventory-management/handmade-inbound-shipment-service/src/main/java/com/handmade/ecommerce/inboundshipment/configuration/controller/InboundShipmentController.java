package com.handmade.ecommerce.inboundshipment.configuration.controller;

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
import com.handmade.ecommerce.inventory.model.InboundShipment;

@RestController
@ChenileController(value = "inboundshipmentService", serviceName = "_inboundshipmentStateEntityService_",
		healthCheckerName = "inboundshipmentHealthChecker")
public class InboundShipmentController extends ControllerSupport{
	
	@GetMapping("/inboundshipment/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<InboundShipment>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/inboundshipment")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<InboundShipment>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody InboundShipment entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/inboundshipment/{id}/{eventID}")
	@BodyTypeSelector("inboundshipmentBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<InboundShipment>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
