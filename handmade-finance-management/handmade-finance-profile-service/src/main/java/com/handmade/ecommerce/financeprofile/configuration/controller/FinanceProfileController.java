package com.handmade.ecommerce.financeprofile.configuration.controller;

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
import com.handmade.ecommerce.finance.model.FinanceProfile;

@RestController
@ChenileController(value = "financeprofileService", serviceName = "_financeprofileStateEntityService_",
		healthCheckerName = "financeprofileHealthChecker")
public class FinanceProfileController extends ControllerSupport{
	
	@GetMapping("/financeprofile/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<FinanceProfile>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/financeprofile")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<FinanceProfile>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody FinanceProfile entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/financeprofile/{id}/{eventID}")
	@BodyTypeSelector("financeprofileBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<FinanceProfile>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
