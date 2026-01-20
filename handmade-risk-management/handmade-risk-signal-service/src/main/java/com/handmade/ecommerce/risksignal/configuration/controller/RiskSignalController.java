package com.handmade.ecommerce.risksignal.configuration.controller;

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
import com.handmade.ecommerce.risk.model.RiskSignal;

@RestController
@ChenileController(value = "risksignalService", serviceName = "_risksignalStateEntityService_",
		healthCheckerName = "risksignalHealthChecker")
public class RiskSignalController extends ControllerSupport{
	
	@GetMapping("/risksignal/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<RiskSignal>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/risksignal")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<RiskSignal>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody RiskSignal entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/risksignal/{id}/{eventID}")
	@BodyTypeSelector("risksignalBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<RiskSignal>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
