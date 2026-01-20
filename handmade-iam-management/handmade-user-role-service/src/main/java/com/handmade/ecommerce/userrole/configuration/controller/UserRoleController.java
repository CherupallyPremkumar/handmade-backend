package com.handmade.ecommerce.userrole.configuration.controller;

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
import com.handmade.ecommerce.iam.model.UserRole;

@RestController
@ChenileController(value = "userroleService", serviceName = "_userroleStateEntityService_",
		healthCheckerName = "userroleHealthChecker")
public class UserRoleController extends ControllerSupport{
	
	@GetMapping("/userrole/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<UserRole>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/userrole")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<UserRole>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody UserRole entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/userrole/{id}/{eventID}")
	@BodyTypeSelector("userroleBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<UserRole>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
