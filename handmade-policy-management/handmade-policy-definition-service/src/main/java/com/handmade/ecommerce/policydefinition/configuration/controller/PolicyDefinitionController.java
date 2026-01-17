package com.handmade.ecommerce.policydefinition.configuration.controller;

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
import com.handmade.ecommerce.policy.model.PolicyDefinition;

@RestController
@ChenileController(value = "policydefinitionService", serviceName = "_policydefinitionStateEntityService_",
		healthCheckerName = "policydefinitionHealthChecker")
public class PolicyDefinitionController extends ControllerSupport{
	
	@GetMapping("/policydefinition/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PolicyDefinition>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/policydefinition")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PolicyDefinition>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody PolicyDefinition entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/policydefinition/{id}/{eventID}")
	@BodyTypeSelector("policydefinitionBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PolicyDefinition>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
