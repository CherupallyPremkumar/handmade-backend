package com.handmade.ecommerce.dispute.configuration.controller;

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
import com.handmade.ecommerce.dispute.model.Dispute;

@RestController
@ChenileController(value = "disputeService", serviceName = "_disputeStateEntityService_",
		healthCheckerName = "disputeHealthChecker")
public class DisputeController extends ControllerSupport{
	
	@GetMapping("/dispute/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Dispute>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/dispute")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Dispute>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Dispute entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/dispute/{id}/{eventID}")
	@BodyTypeSelector("disputeBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Dispute>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
