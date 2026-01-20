package com.handmade.ecommerce.notificationlog.configuration.controller;

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
import com.handmade.ecommerce.notification.model.NotificationLog;

@RestController
@ChenileController(value = "notificationlogService", serviceName = "_notificationlogStateEntityService_",
		healthCheckerName = "notificationlogHealthChecker")
public class NotificationLogController extends ControllerSupport{
	
	@GetMapping("/notificationlog/{id}")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<NotificationLog>>> retrieve(
			HttpServletRequest httpServletRequest,
			@PathVariable String id){
		return process(httpServletRequest,id);
	}

	@PostMapping("/notificationlog")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<NotificationLog>>> create(
			HttpServletRequest httpServletRequest,
			@ChenileParamType(StateEntity.class)
			@RequestBody NotificationLog entity){
		return process(httpServletRequest,entity);
	}

	
	@PatchMapping("/notificationlog/{id}/{eventID}")
	@BodyTypeSelector("notificationlogBodyTypeSelector")
	public ResponseEntity<GenericResponse<StateEntityServiceResponse<NotificationLog>>> processById(
			HttpServletRequest httpServletRequest,
			@PathVariable String id,
			@PathVariable String eventID,
			@ChenileParamType(Object.class) 
			@RequestBody String eventPayload){
		return process(httpServletRequest,id,eventID,eventPayload);
	}


}
