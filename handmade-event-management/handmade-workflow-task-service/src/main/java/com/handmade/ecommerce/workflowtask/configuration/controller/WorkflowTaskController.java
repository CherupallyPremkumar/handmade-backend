package com.handmade.ecommerce.workflowtask.configuration.controller;

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
import com.handmade.ecommerce.event.model.WorkflowTask;

@RestController
@ChenileController(value = "workflowtaskService", serviceName = "_workflowtaskStateEntityService_",
		healthCheckerName = "workflowtaskHealthChecker")
public class WorkflowTaskController extends ControllerSupport{
	
	@GetMapping("/workflowtask/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<WorkflowTask>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/workflowtask")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<WorkflowTask>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody WorkflowTask entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/workflowtask/{id}/{eventID}")
	@BodyTypeSelector("workflowtaskBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<WorkflowTask>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
