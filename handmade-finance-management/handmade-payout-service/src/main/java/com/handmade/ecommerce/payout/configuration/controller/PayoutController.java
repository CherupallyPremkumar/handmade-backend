package com.handmade.ecommerce.payout.configuration.controller;

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
import com.handmade.ecommerce.finance.model.Payout;

@RestController
@ChenileController(value = "payoutService", serviceName = "_payoutStateEntityService_",
		healthCheckerName = "payoutHealthChecker")
public class PayoutController extends ControllerSupport{
	
	@GetMapping("/payout/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Payout>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/payout")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Payout>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Payout entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/payout/{id}/{eventID}")
	@BodyTypeSelector("payoutBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Payout>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
