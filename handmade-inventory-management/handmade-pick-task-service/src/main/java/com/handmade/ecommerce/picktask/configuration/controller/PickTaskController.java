package com.handmade.ecommerce.picktask.configuration.controller;

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
import com.handmade.ecommerce.inventory.model.PickTask;

@RestController
@ChenileController(value = "picktaskService", serviceName = "_picktaskStateEntityService_",
		healthCheckerName = "picktaskHealthChecker")
public class PickTaskController extends ControllerSupport{
	
	@GetMapping("/picktask/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PickTask>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/picktask")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PickTask>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody PickTask entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/picktask/{id}/{eventID}")
	@BodyTypeSelector("picktaskBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<PickTask>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
