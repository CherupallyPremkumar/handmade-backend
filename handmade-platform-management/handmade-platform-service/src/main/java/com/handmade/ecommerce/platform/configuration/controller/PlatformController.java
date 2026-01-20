package com.handmade.ecommerce.platform.configuration.controller;

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
import com.handmade.ecommerce.platform.model.Platform;

@RestController
@ChenileController(value = "platformService", serviceName = "_platformStateEntityService_",
		healthCheckerName = "platformHealthChecker")
public class PlatformController extends ControllerSupport{
	
	@GetMapping("/platform/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Platform>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/platform")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Platform>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Platform entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/platform/{id}/{eventID}")
	@BodyTypeSelector("platformBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Platform>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
