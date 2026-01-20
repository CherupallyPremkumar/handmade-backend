package com.handmade.ecommerce.fraudcase.configuration.controller;

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
import com.handmade.ecommerce.risk.model.FraudCase;

@RestController
@ChenileController(value = "fraudcaseService", serviceName = "_fraudcaseStateEntityService_",
		healthCheckerName = "fraudcaseHealthChecker")
public class FraudCaseController extends ControllerSupport{
	
	@GetMapping("/fraudcase/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<FraudCase>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/fraudcase")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<FraudCase>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody FraudCase entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/fraudcase/{id}/{eventID}")
	@BodyTypeSelector("fraudcaseBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<FraudCase>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
