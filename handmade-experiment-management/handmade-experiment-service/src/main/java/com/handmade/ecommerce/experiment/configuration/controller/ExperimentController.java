package com.handmade.ecommerce.experiment.configuration.controller;

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
import com.handmade.ecommerce.experiment.model.Experiment;

@RestController
@ChenileController(value = "experimentService", serviceName = "_experimentStateEntityService_",
		healthCheckerName = "experimentHealthChecker")
public class ExperimentController extends ControllerSupport{
	
	@GetMapping("/experiment/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Experiment>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/experiment")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Experiment>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Experiment entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/experiment/{id}/{eventID}")
	@BodyTypeSelector("experimentBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Experiment>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
