package com.handmade.ecommerce.permission.configuration.controller;

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
import com.handmade.ecommerce.iam.model.Permission;

@RestController
@ChenileController(value = "permissionService", serviceName = "_permissionStateEntityService_",
		healthCheckerName = "permissionHealthChecker")
public class PermissionController extends ControllerSupport{
	
	@GetMapping("/permission/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Permission>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/permission")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Permission>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Permission entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/permission/{id}/{eventID}")
	@BodyTypeSelector("permissionBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Permission>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
