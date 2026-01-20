package com.handmade.ecommerce.cyclecount.configuration.controller;

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
import com.handmade.ecommerce.inventory.model.CycleCount;

@RestController
@ChenileController(value = "cyclecountService", serviceName = "_cyclecountStateEntityService_",
		healthCheckerName = "cyclecountHealthChecker")
public class CycleCountController extends ControllerSupport{
	
	@GetMapping("/cyclecount/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<CycleCount>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/cyclecount")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<CycleCount>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody CycleCount entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/cyclecount/{id}/{eventID}")
	@BodyTypeSelector("cyclecountBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<CycleCount>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
