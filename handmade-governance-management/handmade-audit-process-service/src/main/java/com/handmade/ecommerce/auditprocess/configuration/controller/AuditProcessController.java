package com.handmade.ecommerce.auditprocess.configuration.controller;

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
import com.handmade.ecommerce.governance.model.AuditProcess;

@RestController
@ChenileController(value = "auditprocessService", serviceName = "_auditprocessStateEntityService_",
		healthCheckerName = "auditprocessHealthChecker")
public class AuditProcessController extends ControllerSupport{
	
	@GetMapping("/auditprocess/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<AuditProcess>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/auditprocess")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<AuditProcess>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody AuditProcess entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/auditprocess/{id}/{eventID}")
	@BodyTypeSelector("auditprocessBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<AuditProcess>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
