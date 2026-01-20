package com.handmade.ecommerce.role.configuration.controller;

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
import com.handmade.ecommerce.iam.model.Role;

@RestController
@ChenileController(value = "roleService", serviceName = "_roleStateEntityService_",
		healthCheckerName = "roleHealthChecker")
public class RoleController extends ControllerSupport{
	
	@GetMapping("/role/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Role>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/role")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Role>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody Role entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/role/{id}/{eventID}")
	@BodyTypeSelector("roleBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<Role>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
