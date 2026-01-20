package com.handmade.ecommerce.packtask.configuration.controller;

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
import com.handmade.ecommerce.inventory.model.PackTask;

@RestController
@ChenileController(value = "packtaskService", serviceName = "_packtaskStateEntityService_",
		healthCheckerName = "packtaskHealthChecker")
public class PackTaskController extends ControllerSupport{
	
	@GetMapping("/packtask/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PackTask>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/packtask")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PackTask>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody PackTask entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/packtask/{id}/{eventID}")
	@BodyTypeSelector("packtaskBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PackTask>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
