package com.handmade.ecommerce.etljob.configuration.controller;

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
import com.handmade.ecommerce.integration.model.ETLJob;

@RestController
@ChenileController(value = "etljobService", serviceName = "_etljobStateEntityService_",
		healthCheckerName = "etljobHealthChecker")
public class ETLJobController extends ControllerSupport{
	
	@GetMapping("/etljob/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ETLJob>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/etljob")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ETLJob>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody ETLJob entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/etljob/{id}/{eventID}")
	@BodyTypeSelector("etljobBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ETLJob>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
