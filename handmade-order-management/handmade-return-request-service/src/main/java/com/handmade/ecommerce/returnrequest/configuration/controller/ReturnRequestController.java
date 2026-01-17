package com.handmade.ecommerce.returnrequest.configuration.controller;

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
import com.handmade.ecommerce.order.model.ReturnRequest;

@RestController
@ChenileController(value = "returnrequestService", serviceName = "_returnrequestStateEntityService_",
		healthCheckerName = "returnrequestHealthChecker")
public class ReturnRequestController extends ControllerSupport{
	
	@GetMapping("/returnrequest/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ReturnRequest>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/returnrequest")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ReturnRequest>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody ReturnRequest entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/returnrequest/{id}/{eventID}")
	@BodyTypeSelector("returnrequestBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<ReturnRequest>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
