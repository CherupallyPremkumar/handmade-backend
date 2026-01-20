package com.handmade.ecommerce.inboundshipmentitem.configuration.controller;

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
import com.handmade.ecommerce.inventory.model.InboundShipmentItem;

@RestController
@ChenileController(value = "inboundshipmentitemService", serviceName = "_inboundshipmentitemStateEntityService_",
		healthCheckerName = "inboundshipmentitemHealthChecker")
public class InboundShipmentItemController extends ControllerSupport{
	
	@GetMapping("/inboundshipmentitem/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<InboundShipmentItem>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/inboundshipmentitem")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<InboundShipmentItem>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody InboundShipmentItem entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/inboundshipmentitem/{id}/{eventID}")
	@BodyTypeSelector("inboundshipmentitemBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<InboundShipmentItem>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
