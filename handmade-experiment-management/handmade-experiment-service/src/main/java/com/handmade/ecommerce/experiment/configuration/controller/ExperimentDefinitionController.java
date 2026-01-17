package com.handmade.ecommerce.experiment.configuration.controller;

import com.handmade.ecommerce.experiment.model.ExperimentDefinition;
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
@ChenileController(value = "experimentDefinitionService", serviceName = "_experimentDefinitionStateEntityService_",
		healthCheckerName = "experimentDefinitionHealthChecker")
public class ExperimentDefinitionController extends ControllerSupport{
	
	@GetMapping("/experimentDefinition/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ExperimentDefinition>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/experimentDefinition")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ExperimentDefinition>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody ExperimentDefinition entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/experimentDefinition/{id}/{eventID}")
	@BodyTypeSelector("experimentDefinitionBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ExperimentDefinition>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
