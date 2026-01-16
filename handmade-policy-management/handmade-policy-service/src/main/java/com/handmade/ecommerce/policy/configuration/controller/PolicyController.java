package com.handmade.ecommerce.policy.configuration.controller;

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
import com.handmade.ecommerce.policy.model.Policy;

@RestController
@ChenileController(value = "policyService", serviceName = "_policyStateEntityService_",
		healthCheckerName = "policyHealthChecker")
public class PolicyController extends ControllerSupport{
	
	@GetMapping("/policy/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Policy>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/policy")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Policy>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Policy entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/policy/{id}/{eventID}")
	@BodyTypeSelector("policyBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Policy>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
